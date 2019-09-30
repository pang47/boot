package com.taoboot.mini.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 定时任务
 * 〈〉
 *
 * @author chentao
 * @create 2019/5/31
 * @since 1.0.0
 */
@Component
public class ScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    //@Scheduled(cron = "0 */5 * * * ?")
    public void doTask(){
        Map map = new HashMap<String, String>();
        map.put("reqData","{\"source\":0,\"sharePin\":\"7shPo28N444IB69KXKfJCA\"}");

        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        //header.put("Content-Length", "78");
        header.put("Origin", "https://u.jr.jd.com");
        header.put("Referer", "https://u.jr.jd.com/wxgrowing/moneytree/index.html?channelLV=&shareType=1&sharePin=7shPo28N444IB69KXKfJCA&utm_source=iOS*url*1559263992494&utm_medium=wechatshare&utm_campaign=cFNOux1KhoOuSu0GcCg0rg==**11&utm_term=&from=singlemessage&isappinstalled=0");
        header.put("User-Agent", "MICROMESSENGER");
        header.put("Connection", "keep-alive");
        //header.put("Host", "ms.jr.jd.com");
        header.put("Cache-Control", "no-cache");
        header.put("Pragma", "no-cache");
        header.put("Accept-Enconding", "gzip, deflate, br");
        header.put("Accept-Language", "zh-CN,zh;q=0.9");

        //cookie值修改
        String cookies = "__jdu=78480226; areaId=16; ipLoc-djd=16-1315-3486-0; PCSYCityID=1315; shshshfpb=0985d61a006a84ff299e7f9260ed5e1cabe755024af4e14295b7ab312e; shshshfpa=5e1c5952-b355-ea0d-fcb4-8906285ee0fb-1558803281; TrackID=1i5SpALmQNIsCqZT-ll8KShxxWvY0Mi7tUn3WS7J3VX06PUkqfeTYH5E45qhHDrm3g19lZ4GR6CtIZ_isWGPLK_NFtK1oVITGrfTUNn3rkAQ; pinId=HrU0kWqgsKB5SBbp-m3dqrV9-x-f3wj7; pin=jd_69469f4ef2092; unick=oooct; _tp=9RjnqP2YZsjk1LY6CIo1GCsj7cDO4pEZsQVlY7GYY20%3D; _pst=jd_69469f4ef2092; unpl=V2_ZzNtbRYFQxwmXxRXfh1bA2JWFlsRA0YScQwUU30RCwFlVEFZclRCFX0UR1BnG10UZwYZXUdcQRdFCEdkeBBVAWMDE1VGZxBFLV0CFSNGF1wjU00zQwBBQHcJFF0uSgwDYgcaDhFTQEJ2XBVQL0oMDDdRFAhyZ0AVRQhHZH0QWQxmABFacmdEJUU4RVB5GVQHVwIiXHIVF0lyDkFVflRaDGIKE15BUHMURQs%3d; __jdv=122270672|baidu-pinzhuan|t_288551095_baidupinzhuan|cpc|0f3d30c8dba7459bb52f2eb5eba8ac7d_0_ec18bfc245674d57be4655c679f53fb5|1559196873605; __jrr=6C890DA132E80F3685EF4781E180C7; qd_ad=iOS*url*1559263992494%7CcFNOux1KhoOuSu0GcCg0rg%7Cwechatshare%7C-%7C10; qd_uid=JWBFT2TT-2JBSZPTQUH9293KWCHVE; qd_fs=1559267831436; qd_ls=1559267831436; qd_ts=1559267831436; qd_sq=1; qd_sid=JWBFT2TT-2JBSZPTQUH9293KWCHVE-1; shshshfp=43358525fc649b78c93ca29a49b7f529; mba_muid=78480226; TrackerID=7CCt4e9ySPsXRfXp7F-KIX7MWoPXUO97O2LxOIDPCHljgn9BcReBvUOM4o3UMsHoHmDxPrBbgK56fxakVPgeCw; pt_key=AAJc8Ip-ADAAW-mNbaw_CR6dWPa_PjLK2DVLIlu7EvB3ySJ-7ZcEW_OD0KlGWEIsWYs2x7lxoSo; pt_pin=jd_69469f4ef2092; pt_token=1ovu4yxz; pwdt_id=jd_69469f4ef2092; __jd_ref_cls=MLoginRegister_SMSLoginSuccess; __jda=168871293.78480226.1558423167.1559196874.1559267835.8; __jdc=168871293; _jrda=1; 3AB9D23F7A4B3C9B=5REP2RNPWYD3TGSKEBJUG7P2A34T2SD65UPLDL6ZNU7DBMCYX5AYLHXEH22MV4GI5CYHVRHHL5GUSXD3MHTMAOGH2M";
        logger.info("---------------------京东定时任务 开始------------------------");
        String ret = HttpUtils.doPost("https://ms.jr.jd.com/gw/generic/uc/h5/m/harvest?_=" + System.currentTimeMillis(), map, header, cookies);
        logger.info("返回信息:{}", ret);
        logger.info("---------------------京东定时任务 结束------------------------");
    }

}