package com.taoboot.mini.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈时间处理器〉
 *
 * @author chentao
 * @create 2019/9/3
 * @since 1.0.0
 */
public class DateUtil {

    public static String getCurrDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        return df.format(new Date());   // new Date()为获取当前系统时间
    }

    public static String getCurrTime(){
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");//设置日期格式
        return df.format(new Date());   // new Date()为获取当前系统时间
    }

    /**
     * yyyyMMdd ->MM月DD日 星期X
     * @param date
     * @return
     */
    public static String formatDateMMDD(String date){
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String ret = "";
        try{
            ret = month + "月" + day + "日  " + getWeekOfDate(simpleDateFormat.parse(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public static String getWeekOfDate(Date date) {
        /**
         *
         * 功能描述: 根据日期获取星期几
         *
         * @date: 2018/4/3 下午1:46
         * @param: [date]
         * @return: java.lang.String
         *
         */
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getDateByInput(int delay){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        //一周以前
        calendar.setTime(now);
        calendar.add(Calendar.DATE, delay);
        String formatDate = format.format(calendar.getTime());
        return formatDate;
    }

    public static void main(String[] args) {
        System.out.println(getDateByInput(-7));
    }
}