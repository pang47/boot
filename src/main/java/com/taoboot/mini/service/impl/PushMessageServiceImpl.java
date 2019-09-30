package com.taoboot.mini.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.taoboot.mini.core.constant.UserConstant;
import com.taoboot.mini.core.dao.IFormInfoMapper;
import com.taoboot.mini.model.FormInfoDTO;
import com.taoboot.mini.model.TransRecordDTO;
import com.taoboot.mini.service.IPushMessageService;
import com.taoboot.mini.service.ITransRecordService;
import com.taoboot.mini.third.wx.WxApiUtils;
import com.taoboot.mini.util.DateUtil;
import com.taoboot.mini.util.TaoMiniUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
@Service
public class PushMessageServiceImpl implements IPushMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushMessageServiceImpl.class);

    @Autowired
    IFormInfoMapper formInfoMapper;

    @Autowired
    ITransRecordService transRecordService;

    @Override
    public void saveFormInfo(FormInfoDTO dto) {
        formInfoMapper.saveFormInfo(dto);
    }

    /**
     * 推送消息
     */
    @Override
    public void pushMessage(String tempeleId) {
        //获取TAO推送
//        String[] strs = {"2016年8月8日", "银行转账", "陈涛", "100元", "商户已接单", "备注"};
//        JSONObject data = new JSONObject();
//        for (int i = 0; i < strs.length; i++) {
//            JSONObject value = new JSONObject();
//            value.put("value", strs[i]);
//            data.put("keyword" + (i + 1), value);
//        }
        String[] taoStrs = new String[4];
        List<TransRecordDTO> taoTrans = transRecordService.getRecordByUserAndDate(UserConstant.TAO, DateUtil.getCurrDate());
        taoStrs[0] = DateUtil.getCurrDate();
        taoStrs[1] = UserConstant.TAOUSER.getUserName();
        double total = 0;
        String msg = "其中";
        for(TransRecordDTO dto : taoTrans){
            total += Double.parseDouble(dto.getMoney());
            String remark = StringUtils.isEmpty(dto.getRemark())?"":"(" + dto.getRemark() + ")";
            msg += TaoMiniUtils.getTransTypeName(dto.getTransType()) + dto.getMoney() + remark + ";";
        }

        String[] sqStrs = new String[4];
        List<TransRecordDTO> sqTrans = transRecordService.getRecordByUserAndDate(UserConstant.SIQI, DateUtil.getCurrDate());
        sqStrs[0] = DateUtil.getCurrDate();
        sqStrs[1] = UserConstant.SIQIUSER.getUserName();
        double sqtotal = 0;
        String sqMsg = "其中";
        for(TransRecordDTO dto : sqTrans){
            sqtotal += Double.parseDouble(dto.getMoney());
            String remark = StringUtils.isEmpty(dto.getRemark())?"":"(" + dto.getRemark() + ")";
            sqMsg += TaoMiniUtils.getTransTypeName(dto.getTransType()) + dto.getMoney() + remark + ";";
        }

        //modify by chentao
        //小谢要求所有金额
        double all = total + sqtotal;
        taoStrs[2] = all + "";
        sqStrs[2] = all + "";

        taoStrs[3] = "今日"+ UserConstant.TAOUSER.getUserName() +"花了" + total + "元," + "今日"+ UserConstant.SIQIUSER.getUserName() +"花了" + sqtotal + "元。\n" + msg;
        sqStrs[3] = "今日"+ UserConstant.SIQIUSER.getUserName() +"花了" + sqtotal + "元," + "今日"+ UserConstant.TAOUSER.getUserName() +"花了" + total + "元。\n" + sqMsg;


        FormInfoDTO taoForm = formInfoMapper.getFormInfoByOpenId(UserConstant.TAO);
        if (taoForm == null) {
            LOGGER.info("推送信息不足,TAO");
        } else {
            taoForm.setPushDate(DateUtil.getCurrDate());
            taoForm.setPushTime(DateUtil.getCurrTime());
            taoForm.setPushMsg(getSendMsg(taoStrs).toJSONString());
            taoForm.setTemplete(tempeleId);
            taoForm = WxApiUtils.pushMessage(taoForm);
            formInfoMapper.updateFormInfo(taoForm);
        }


        //获取SIQI推送

        FormInfoDTO SQForm = formInfoMapper.getFormInfoByOpenId(UserConstant.SIQI);
        if (SQForm == null) {
            LOGGER.info("推送信息不足,SIQI");
        } else {
            SQForm.setPushDate(DateUtil.getCurrDate());
            SQForm.setPushTime(DateUtil.getCurrTime());
            SQForm.setPushMsg(getSendMsg(sqStrs).toJSONString());
            SQForm.setTemplete(tempeleId);
            SQForm = WxApiUtils.pushMessage(SQForm);
            formInfoMapper.updateFormInfo(SQForm);
        }

    }

    @Override
    public void pushMessage(String[] data, String templeteId) {
        FormInfoDTO taoForm = formInfoMapper.getFormInfoByOpenId(UserConstant.TAO);
        if (taoForm == null) {
            LOGGER.info("推送信息不足,TAO");
        }else{
            taoForm.setPushDate(DateUtil.getCurrDate());
            taoForm.setPushTime(DateUtil.getCurrTime());
            taoForm.setPushMsg(getSendMsg(data).toJSONString());
            taoForm.setTemplete(templeteId);
            taoForm = WxApiUtils.pushMessage(taoForm);
        }

        FormInfoDTO SQForm = formInfoMapper.getFormInfoByOpenId(UserConstant.SIQI);
        if (SQForm == null) {
            LOGGER.info("推送信息不足,SIQI");
        } else {
            SQForm.setPushDate(DateUtil.getCurrDate());
            SQForm.setPushTime(DateUtil.getCurrTime());
            SQForm.setPushMsg(getSendMsg(data).toJSONString());
            SQForm.setTemplete(templeteId);
            SQForm = WxApiUtils.pushMessage(SQForm);
            formInfoMapper.updateFormInfo(SQForm);
        }
    }

    private JSONObject getSendMsg(String[] strs){
        JSONObject data = new JSONObject();
        for (int i = 0; i < strs.length; i++) {
            JSONObject value = new JSONObject();
            value.put("value", strs[i]);
            data.put("keyword" + (i + 1), value);
        }
        return data;
    }

}