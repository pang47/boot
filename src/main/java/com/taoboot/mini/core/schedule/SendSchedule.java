package com.taoboot.mini.core.schedule;

import com.taoboot.mini.core.constant.TaoMiniConstant;
import com.taoboot.mini.service.IPushMessageService;
import com.taoboot.mini.service.ITransRecordService;
import com.taoboot.mini.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 定时发送任务
 *
 * @author chentao
 * @create 2019/9/6
 * @since 1.0.0
 */
@Component
public class SendSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendSchedule.class);

    @Autowired
    IPushMessageService pushMessageService;

    @Autowired
    ITransRecordService transRecordService;

    /**
     * 每晚23点发送
     *
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void sendDailyMsg(){
        LOGGER.info("开始发送定时交易信息,{},{}", DateUtil.getCurrDate(), DateUtil.getCurrTime());

        try{
            pushMessageService.pushMessage(TaoMiniConstant.TEMPLETEIDTRANS);
        }catch (Exception e){
            LOGGER.error("发送失败:{}", e.getMessage());
            LOGGER.error("失败", e);
        }
        LOGGER.info("发送成功");

    }

    @Scheduled(cron = "0 05 23 ? * Sun")
    public void sendWeekMsg(){
        LOGGER.info("开始发送周交易报,{},{}", DateUtil.getCurrDate(), DateUtil.getCurrTime());

        try{
            pushMessageService.pushMessage(transRecordService.getTransReportWeek(), TaoMiniConstant.TEMPLETEIDWEEK);
        }catch (Exception e){
            LOGGER.error("发送失败:{}", e.getMessage());
            LOGGER.error("失败", e);
        }
        LOGGER.info("发送成功");

    }

    @Scheduled(cron = "0 30 23 28-31 * ?")
    public void sendMonth(){
        LOGGER.info("开始发送月交易报,{},{}", DateUtil.getCurrDate(), DateUtil.getCurrTime());

        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            try{
                pushMessageService.pushMessage(transRecordService.getTransReportMonth(), TaoMiniConstant.TEMPLETEIDMONTH);
            }catch (Exception e){
                LOGGER.error("发送失败:{}", e.getMessage());
                LOGGER.error("失败", e);
            }
        }

        LOGGER.info("发送成功");

    }

}