package com.taoboot.mini.controller;


import com.alibaba.fastjson.JSON;
import com.taoboot.mini.core.common.Result;
import com.taoboot.mini.core.constant.TaoMiniConstant;
import com.taoboot.mini.model.FormInfoDTO;
import com.taoboot.mini.service.IPushMessageService;
import com.taoboot.mini.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自动推送信息
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
@RestController
@RequestMapping(value="/msg")
public class PushMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushMessageController.class);

    @Autowired
    IPushMessageService pushMessageService;

    /**
     * 上传推送ID，多存点，界面上多一点FORM
     * @param formInfoDTO
     * @return
     */
    @RequestMapping(value="uploadFormId", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadFormId(FormInfoDTO formInfoDTO){
        LOGGER.info("上传推送ID:{}", JSON.toJSONString(formInfoDTO));

        Result result = new Result();
        try{
            //设置初始状态
            formInfoDTO.setStatus(TaoMiniConstant.FORMSTATUSINIT);
            formInfoDTO.setCrtDate(DateUtil.getCurrDate());
            pushMessageService.saveFormInfo(formInfoDTO);

            //TEST
            //pushMessageService.pushMessage(TaoMiniConstant.TEMPLETEIDTRANS);
        }catch (Exception e){
            LOGGER.error("记录FORMID失败", e);
        }
        //界面不管返回
        return result;
    }
}