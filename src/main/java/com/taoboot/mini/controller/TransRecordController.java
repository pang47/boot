package com.taoboot.mini.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taoboot.mini.core.common.Result;
import com.taoboot.mini.core.common.TaoMiniException;
import com.taoboot.mini.core.constant.UserConstant;
import com.taoboot.mini.model.TransRecordDTO;
import com.taoboot.mini.model.vo.TransRecordVO;
import com.taoboot.mini.service.ITransRecordService;
import com.taoboot.mini.util.TaoMiniUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  交易信息
 *
 * @author chentao
 * @create 2019/9/2
 * @since 1.0.0
 */

@RestController
@RequestMapping(value = "record")
public class TransRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransRecordController.class);

    @Autowired
    private ITransRecordService transRecordService;


    /**
     * 上传日记账内容
     * @param transRecordDTO
     * @return
     */
    @RequestMapping(value = "uploadTransRecord", method = RequestMethod.POST)
    public Result record(TransRecordDTO transRecordDTO){
        LOGGER.info("保存交易流水:{}", JSON.toJSONString(transRecordDTO));
        Result result = new Result();
        try{
            String openId = transRecordDTO.getUser();
            if(StringUtils.isEmpty(openId))
                throw new TaoMiniException();

            //设置姓名
            UserConstant user = openId.equals(UserConstant.TAO)?UserConstant.TAOUSER:UserConstant.SIQIUSER;
            transRecordDTO.setUserName(user.getUserName());

            //保存交易记录
            transRecordService.saveRecord(transRecordDTO);

        } catch(TaoMiniException e){
            LOGGER.error("业务错误,{},{}", e.getErrorCode(), e.getErrorMsg());
            result.setRetMsg(e.getErrorMsg());
            result.setSucc(false);
        } catch (Exception e){
            LOGGER.error("请求失败", e);
            result.setRetMsg("交易失败" + e.getMessage());
            result.setSucc(false);
        }

        return result;
    }

    @RequestMapping(value = "getTransRecordByUser", method = RequestMethod.POST)
    public Result getTransRecordByUser(String openId, String transDate){

        LOGGER.info("查询交易记录:{}, {}", openId, transDate);

        Result result = new Result();

        try{
            List<TransRecordVO> list = transRecordService.getRecordByUserAndDatePage(openId, transDate);
            result.setData(list);
            result.setSucc(true);
        }catch(Exception e){
            LOGGER.error("查询失败:{}", e);
            result.setSucc(false);
        }

        return result;
    }

    @RequestMapping(value = "/getPayByMonth", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayByMonth(String month){
        Result result = new Result();

        result.setSucc(true);
        result.setData(transRecordService.getPayByMonth(month));

        return result;
    }

    @RequestMapping(value = "/getTransTypeList", method = RequestMethod.POST)
    @ResponseBody
    public Result getTransTypeList(){
        Result result = new Result();

        result.setSucc(true);

        JSONArray arr = new JSONArray();
        for(int i=0; i<18; i++){
            JSONObject object = new JSONObject();
            int transType = i+1;
            object.put("transType", transType);
            object.put("transTypeName", TaoMiniUtils.getTransTypeName(transType+""));
            object.put("imageUrl", TaoMiniUtils.getTransImageUrl(transType+""));
            object.put("imageUrlActive", TaoMiniUtils.getTransActiveImageUrl(transType + ""));
            arr.add(object);
        }

        result.setData(arr);

        return result;
    }

    @RequestMapping(value = "/getTransRerord", method = RequestMethod.POST)
    @ResponseBody
    public Result getTransRecord(String transId){
        Result result = new Result();

        result.setSucc(true);
        result.setData(transRecordService.getTransRecord(transId));

        return result;
    }

    @RequestMapping(value = "/updateTransRerord", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTransRerord(TransRecordDTO dto){
        Result result = new Result();
        LOGGER.info("更新数据:{}", JSON.toJSONString(dto));
        try{
            result.setSucc(true);
            transRecordService.updateTransRecord(dto);
        }catch (Exception e){
            LOGGER.error("更新失败,{}", e);
            result.setSucc(false);
        }

        return result;
    }

    @RequestMapping(value = "/deleteTransRerord", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTransRerord(String transId){
        Result result = new Result();

        try{
            result.setSucc(true);
            transRecordService.deleteTransRecord(transId);
        }catch (Exception e){
            LOGGER.error("删除失败,{}", transId, e);
            result.setSucc(false);
        }

        return result;
    }

}