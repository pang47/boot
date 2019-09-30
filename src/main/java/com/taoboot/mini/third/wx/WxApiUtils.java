package com.taoboot.mini.third.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taoboot.mini.core.constant.CacheConstant;
import com.taoboot.mini.core.constant.TaoMiniConstant;
import com.taoboot.mini.core.ehcache.CacheUtil;
import com.taoboot.mini.model.FormInfoDTO;
import com.taoboot.mini.util.Base64Util;
import com.taoboot.mini.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *  微信接口
 * 〈〉
 *
 * @author chentao
 * @create 2019/5/22
 * @since 1.0.0
 */
@Controller
public class WxApiUtils {
    private static final Logger logger = LoggerFactory.getLogger(WxApiUtils.class);

    private static final String APPID = "wx6f4ad704d05fa575";
    private static final String APPSECRET = "5e23fac7a3699e503a0e62b0ddbe2205";

    private static final String YH_APPSECRET = "362612ed3e6cacc2079d89998d53c816";
    private static final String YH_APPID = "wxdddefd6d81dd7ac9";
    private static final String YH_MCH_ID = "1549457591";
    private static final String YH_CERT_NO = "5706681D5BA5B251A47A0E11A64213FF8AE83603";
    private static final String YH_PRV_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDezcb2shbTPZQH/V0Ed2gIu0EKfUxb2w2F6MjiWsQQhoRtCRO5U15NiwJbOg0/G8EUcIR74uyTxnCKZBwSEE/JbBRkjv8yr9UT00s9MXmg1Zxh1CkLmLIFsC4RVQ3SU5IonksnbGREw0G0ijwEaw1Q+pNY9WOOiZK1Dis4/RoJy7cN/vJEClyHGyHWwjsVzI9068GHxHpApCjyS9rhZ2IKjtPRqeUWGhwzId1CbDe8Rm2124MsqdljOnL1BrmDQbjsuXCDgTNkADEfIFeL0kzElMbpPC4RlN0lxjLrE3ZkFR/9bCR0hgGxRCBonyosbsY6TWkpzZVxEXAkAoO8rQuHAgMBAAECggEAQLumwAw0B4HPnZamTOLobgkBt6t54QI+5pYgr2tRpb9/gDJv8ZtgPcVSlPDD4n8M1QRv6ZKfy7uO/RZi9XiZxdrJ/gFx8ecWUGFGbewO+4S0n9PFbSIbLv3mFJESp/Yx8+0xxq1+YGAemfbDw0DbKJj08JazKzmbT1Fji3Y193wW03O19pAf21eOHBqsHFg9lpyM+Y2+ZuJ6Fr6bnElyxUSW/a0Qsb677Oxt8lDffE+dZn+DQrOPeeTdca002J4MPR9jJbiSpaK/jIEXlF0MPLVbSaHbafr1HppYjhz9QxsWgOvsP3dx+UO7FFLxTfXsk2O/aqY0u6LCQKTeHNrG2QKBgQDyRvgFlnKhip+/VhV2Di8LhbpbU6QDDUM3WnvSXgr6ceXxwN9Ft9y/ugBHLPy1dhM7hG7SGHIRLCCPOlEg9Sxl0LEZOdbu8+RHoEj3Ae5xrPtMMDHNye5QkpfKbC8APGMWa++Dx5nRwvA1Sly7oaUlQ2PKS1hHYVcDZIQVUL4AQwKBgQDrbHFteXCMB/wtvKQbv2Y0c5E9CN2bGlwan4iw6Lg0QUO1W8WMhXBH1xdbSym08djFZbLZaP0spA1axk2pXPwauGK9Q55neNDciArUKByqOV1T9kP/1HqAxsMhir5JfZa5GhDFrvT17z76/uEcXp5pDq2sY5Q4+Ag3mIeTtRnlbQKBgDWl4WXbWqbKu3Ty6u2pVmGGyy1N6BOE7E1li+WZTXUHo5NU0hvuCsOpvEYGgC7uZnPXb3K2VwvWaZ9NmolhwOMKwyyC0kU3XGU/6OhJVTN2ym6kfWLERDEzRMleC0pSFN7yJ6dWm4kHUYl7Xe1ubdNCiOZSqMmllE78AwN1TZ1hAoGBALSq1ilDRuHPxvF6kR5SNicOgWkrgpVjF0e/InFRRvujEkj9ko8SYGZhZKVE+07HQqnKMAS0k4ZMp4sAH91XZIlE81F3sPqLNw3mwRirZ8QincKvfOO5tgFZh6oYBYv0e1S18iUi7Q5Heje4nhzdaAgLobBoAvntLrErtu/dzW/JAoGAW+c61T/ZlE3/H40j16ZAYjxRGodp6kQO7wFDydrjTzwD5yjKHmiQVBIStKw3fZ5MdQ9uZH0VsuM1LU7Fs92/c7/k5gr5KtPjzvcO+K6cp9S0QwhizJ0z7ca+FZMkZjFyBOqzPAOysBkEU6YjogRUf2hm94DrZJvrGE6UKhhE9g8=";

    private static final String WJW_APPID = "wx85dcd5723776f64b";
    private static final String WJW_APPSECRET = "c49744ec1bc513ea7c442749db0cce7a";

    private static final String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

    @ResponseBody
    @RequestMapping(value="getOpenId")
    public String getOpenIdByAuthCode(HttpServletRequest request, String authCode){

        //获取缓存KEY
        String key = request.getSession().getId();
        CacheUtil cacheUtil = CacheUtil.getInstance();

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID +
            "&secret=" + APPSECRET +
            "&js_code=" + authCode +
            "&grant_type=authorization_code";
        JSONObject result = JSON.parseObject(HttpUtils.doGet(url));
        logger.info("获取OPENID返回:{}", JSON.toJSONString(result));
        String openId = result.getString("openid");

        //加入缓存中
        logger.info("放入缓存ID:{},openId:{}", key, openId);
        CacheUtil.getInstance().put(CacheConstant.WXOPENID.getCode(), key, openId);

        return openId;
    }


    public static FormInfoDTO pushMessage(FormInfoDTO form){
        String sendUrl = sendMsgUrl.replace("ACCESS_TOKEN", getAccessToken());
        JSONObject sendJson = new JSONObject();
        sendJson.put("touser", form.getOpenId());
        sendJson.put("template_id", form.getTemplete());
        sendJson.put("form_id", form.getFormId());
        sendJson.put("data", JSON.parseObject(form.getPushMsg()));
        try {
            String retInfo = HttpUtils.request(sendUrl, null, sendJson.toJSONString(), "UTF-8", null);
            JSONObject retObj = JSON.parseObject(retInfo);
            if(retObj.getString("errcode").equals("0")){
                form.setStatus(TaoMiniConstant.FORMSTATUSSUCC);
            }else{
                form.setStatus(TaoMiniConstant.FORMSTATUSFAIL);
                form.setErrMsg(retObj.getString("errmsg"));
            }
        } catch (Exception e) {
            form.setStatus(TaoMiniConstant.FORMSTATUSFAIL);
            form.setErrMsg(e.getMessage());
            logger.error("发送推送消息失败,{}", form.getFormId(), e);
        }

        return form;
    }


    private static String getAccessToken(){
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        JSONObject accessResult = JSON.parseObject(HttpUtils.doGet(accessTokenUrl.replace("APPID", APPID).replace("APPSECRET", APPSECRET)));

        String accessToken = accessResult.getString("access_token");
        logger.info("获取accessToken:{}", accessToken);
        return accessToken;

    }

    public static void main(String[] args) {
        //pushMessage();

    }

    public static String sign(String request, String privateKey) throws Exception {
        byte[] data = request.getBytes("UTF-8");
        byte[] keyBytes = Base64Util.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initSign(privateK);
        signature.update(data);
        return Base64Util.encode(signature.sign());
    }

}