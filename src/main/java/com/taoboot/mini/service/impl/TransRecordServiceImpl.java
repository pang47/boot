package com.taoboot.mini.service.impl;

import com.taoboot.mini.core.constant.TransTypeEnum;
import com.taoboot.mini.core.constant.UserConstant;
import com.taoboot.mini.core.dao.ITransRecordMapper;
import com.taoboot.mini.model.TransRecordDTO;
import com.taoboot.mini.model.vo.TransRecordVO;
import com.taoboot.mini.service.ITransRecordService;
import com.taoboot.mini.util.DateUtil;
import com.taoboot.mini.util.TaoMiniUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
@Service
public class TransRecordServiceImpl implements ITransRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransRecordServiceImpl.class);

    @Autowired
    private ITransRecordMapper transRecordMapper;

    @Override
    public void saveRecord(TransRecordDTO dto) {
        dto.setTransId(UUID.randomUUID().toString());
        if(dto.getTransDate().equals("今天")){
            dto.setTransDate(DateUtil.getCurrDate());
        }
        dto.setCrtDate(DateUtil.getCurrDate());
        dto.setCrtTime(DateUtil.getCurrTime());
        transRecordMapper.saveRecord(dto);
    }

    @Override
    public List<TransRecordDTO> getRecordByUserAndDate(String openId, String currDate) {
        TransRecordDTO dto = new TransRecordDTO();
        dto.setUser(openId);
        dto.setTransDate(currDate);
        return transRecordMapper.getRecordByUserAndDate(dto);
    }

    @Override
    public List<TransRecordVO> getRecordByUser(String openId) {
        TransRecordDTO dto = new TransRecordDTO();
        dto.setUser(openId);
        List<TransRecordDTO> transs = transRecordMapper.getRecordByUser(dto);
        List<TransRecordDTO> sameDate = new ArrayList<>();
        List<TransRecordVO> retList = new ArrayList<>();
        TransRecordVO vo = new TransRecordVO();
        String transDate = "";
        double total = 0;
        for(TransRecordDTO trans : transs){
            trans.setTransTypeName(TaoMiniUtils.getTransTypeName(trans.getTransType()));
            trans.setImageUrl(TaoMiniUtils.getTransActiveImageUrl(trans.getTransType()));
            if(transDate.equals("")){
                //第一次进入
                transDate = trans.getTransDate();
            }

            //日期变了,重置对象
            if(!transDate.equals(trans.getTransDate())){
                //设置对象
                vo.setTotal(total+"");
                vo.setData(sameDate);
                vo.setTransDate(DateUtil.formatDateMMDD(transDate));
                retList.add(vo);
                //重置对象
                transDate = trans.getTransDate();
                vo = new TransRecordVO();
                sameDate = new ArrayList<>();
                total = 0;
                //加上本轮信息
                sameDate.add(trans);
                total += Double.parseDouble(trans.getMoney());
            }else{
                sameDate.add(trans);
                total += Double.parseDouble(trans.getMoney());
            }
        }

        //加上最后一段
        if(!sameDate.isEmpty()){
            vo.setTotal(total+"");
            vo.setData(sameDate);
            vo.setTransDate(DateUtil.formatDateMMDD(transDate));
            retList.add(vo);
        }

        return retList;
    }

    @Override
    public List<TransRecordVO> getRecordByUserAndDatePage(String openId, String _transDate) {
        TransRecordDTO dto = new TransRecordDTO();
        dto.setUser(openId);
        dto.setTransDate(_transDate);
        List<TransRecordDTO> transs = transRecordMapper.getRecordByUserAndDate(dto);
        List<TransRecordDTO> sameDate = new ArrayList<>();
        List<TransRecordVO> retList = new ArrayList<>();
        TransRecordVO vo = new TransRecordVO();
        String transDate = "";
        double total = 0;
        for(TransRecordDTO trans : transs){
            trans.setTransTypeName(TaoMiniUtils.getTransTypeName(trans.getTransType()));
            trans.setImageUrl(TaoMiniUtils.getTransActiveImageUrl(trans.getTransType()));
            if(transDate.equals("")){
                //第一次进入
                transDate = trans.getTransDate();
            }

            //日期变了,重置对象
            if(!transDate.equals(trans.getTransDate())){
                //设置对象
                vo.setTotal(total+"");
                vo.setData(sameDate);
                vo.setTransDate(DateUtil.formatDateMMDD(transDate));
                retList.add(vo);
                //重置对象
                transDate = trans.getTransDate();
                vo = new TransRecordVO();
                sameDate = new ArrayList<>();
                total = 0;
                //加上本轮信息
                sameDate.add(trans);
                total += Double.parseDouble(trans.getMoney());
            }else{
                sameDate.add(trans);
                total += Double.parseDouble(trans.getMoney());
            }
        }

        //加上最后一段
        if(!sameDate.isEmpty()){
            vo.setTotal(total+"");
            vo.setData(sameDate);
            vo.setTransDate(DateUtil.formatDateMMDD(transDate));
            retList.add(vo);
        }

        return retList;
    }

    @Override
    public String getPayByMonth(String month) {
        TransRecordDTO dto = new TransRecordDTO();
        dto.setTransDate(month);
        dto.setUser(UserConstant.TAO);
        double pay = 0;

        List<TransRecordDTO> list = transRecordMapper.getRecordByUserAndDate(dto);
        for(TransRecordDTO res : list){
            pay += Double.parseDouble(res.getMoney());
        }

        dto.setUser(UserConstant.SIQI);
        List<TransRecordDTO> listq = transRecordMapper.getRecordByUserAndDate(dto);
        for(TransRecordDTO res : listq){
            pay += Double.parseDouble(res.getMoney());
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(pay) + "";
    }

    @Override
    public TransRecordDTO getTransRecord(String transId) {
        TransRecordDTO dto = transRecordMapper.getTransRecord(transId);
        dto.setTransTypeName(TaoMiniUtils.getTransTypeName(dto.getTransType()));
        dto.setImageUrl(TaoMiniUtils.getTransActiveImageUrl(dto.getTransType()));
        return dto;
    }

    @Override
    public void deleteTransRecord(String transId) {
        TransRecordDTO dto = new TransRecordDTO();
        dto.setTransId(transId);
        dto.setStatus("0");
        transRecordMapper.updateTransRecordStatus(dto);
    }

    @Override
    public void updateTransRecord(TransRecordDTO dto) {
        transRecordMapper.updateTransRecord(dto);
    }

    @Override
    public String[] getTransReportWeek() {
        String[] strs = {};
        StringBuffer sb = new StringBuffer();

        double totalMoney = 0;

        for(int i=0; i<7; i++){
            String date = DateUtil.getDateByInput(0-i);
            TransRecordDTO tao = new TransRecordDTO();
            tao.setUser(UserConstant.TAO);
            tao.setTransDate(date);
            List<TransRecordDTO> taos = transRecordMapper.getRecordByUserAndDate(tao);

            for(TransRecordDTO dto : taos){
                totalMoney += Double.parseDouble(dto.getMoney());
            }

            TransRecordDTO pang = new TransRecordDTO();
            pang.setUser(UserConstant.SIQI);
            pang.setTransDate(date);
            List<TransRecordDTO> pangs = transRecordMapper.getRecordByUserAndDate(pang);

            for(TransRecordDTO dto : pangs){
                totalMoney += Double.parseDouble(dto.getMoney());
            }
        }
        strs[0] = DateUtil.getCurrDate();
        sb.append("本周花销:");
        sb.append(totalMoney);
        strs[1] = sb.toString();

        return strs;
    }

    @Override
    public String[] getTransReportMonth() {
        String[] strs = new String[3];
        //统计时间，本月支出，记账数目

        strs[0] = format(DateUtil.getCurrDate() + " " + DateUtil.getCurrTime());

        //获取Tao
        String month = DateUtil.getCurrDate().substring(0, 6);
        TransRecordDTO dto = new TransRecordDTO();
        dto.setTransDate(month);
        dto.setUser(UserConstant.TAO);
        List<TransRecordDTO> list = transRecordMapper.getRecordByUserAndDate(dto);

        //获取SQ
        dto.setUser(UserConstant.SIQI);
        List<TransRecordDTO> qlist = transRecordMapper.getRecordByUserAndDate(dto);

        strs[2] = (list.size() + qlist.size()) + "笔";

        StringBuffer detail = new StringBuffer();
        Map<String, String> detailMap = new HashMap();
        for(TransRecordDTO item : list){

            if(!item.getTransType().equals(TransTypeEnum.BREAKFASTTRANS.getTransType()) &&
                    !item.getTransType().equals(TransTypeEnum.LUNCHTRANS.getTransType()) && !item.getTransType().equals(TransTypeEnum.DINNERTRANS.getTransType())){

                if(detailMap.get(TaoMiniUtils.getTransTypeName(item.getTransType())) == null){
                    detailMap.put(TaoMiniUtils.getTransTypeName(item.getTransType()), item.getMoney());
                }else{
                    double money = Double.parseDouble(detailMap.get(TaoMiniUtils.getTransTypeName(item.getTransType())));
                    money += Double.parseDouble(item.getMoney());
                    detailMap.put(TaoMiniUtils.getTransTypeName(item.getTransType()), money+"");
                }
                continue;
            }

            //计算日常伙食
            if(detailMap.get("daily") == null){
                detailMap.put("daily", item.getMoney());
            }else{
                double money = Double.parseDouble(detailMap.get("daily"));
                money += Double.parseDouble(item.getMoney());
                detailMap.put("daily", money+"");
            }
        }

        for(TransRecordDTO item : qlist){
            if(!item.getTransType().equals(TransTypeEnum.BREAKFASTTRANS.getTransType()) &&
                    !item.getTransType().equals(TransTypeEnum.LUNCHTRANS.getTransType()) && !item.getTransType().equals(TransTypeEnum.DINNERTRANS.getTransType())){

                if(detailMap.get(TaoMiniUtils.getTransTypeName(item.getTransType())) == null){
                    detailMap.put(TaoMiniUtils.getTransTypeName(item.getTransType()), item.getMoney());
                }else{
                    double money = Double.parseDouble(detailMap.get(TaoMiniUtils.getTransTypeName(item.getTransType())));
                    money += Double.parseDouble(item.getMoney());
                    detailMap.put(TaoMiniUtils.getTransTypeName(item.getTransType()), money+"");
                }
                continue;
            }

            //计算日常伙食
            if(detailMap.get("daily") == null){
                detailMap.put("daily", item.getMoney());
            }else{
                double money = Double.parseDouble(detailMap.get("daily"));
                money += Double.parseDouble(item.getMoney());
                detailMap.put("daily", money+"");
            }
        }

        detail.append("具体消费如下:\n");
        for(String key : detailMap.keySet()){
            if(key.equals("daily")){
                detail.append("日常伙食:" + detailMap.get(key) + "元。");
            }else{
                detail.append(key + detailMap.get(key) + "元。");
            }
            detail.append("\n");
        }
        detail.append("总计支出:" + this.getPayByMonth(month));
        strs[1] = detail.toString();

        return strs;
    }

    private String format(String date){
        String[] dates = date.split(" ");
        return dates[0].substring(0,4) + "年" + dates[0].substring(4,6) + "月" + dates[0].substring(6, 8) + "日 " + dates[1].substring(0, 2) + ":" + dates[1].substring(2,4) + ":" + dates[1].substring(4,6);
    }
}