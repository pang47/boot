package com.taoboot.mini.third.ali;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZolozIdentificationCustomerCertifyzhubQueryRequest;
import com.alipay.api.response.ZolozIdentificationCustomerCertifyzhubQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 阿里人脸认证接口
 *
 * @author chentao
 * @create 2019/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/ali")
public class AliFaceVerify {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliFaceVerify.class);

    private static final String PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj3TX2Hr91ePGXvnEwbZ2H9OMksvl53sP9I6QUvpXbugMAlGJdJ9VGPN4okzCdr4WEmfK7xz8+NevXw4+aYRNnqpn9vtPvIJYWBaJyLwlIuJy8jyXYmQMUdQpl7ItlK/Ij/PM2g/ASxJb7mLl48Okz42Ac/Lc95EI9p73WXfkEEzNnMDfKNsoSwzO7NhwEkwPaMISS8vuaeq1iiIiFMzV0ly8O8MJ+Sea+awxP7S7iWD886hCLgpgZ1N1GbDT53kbkYaM6CjqG4hftTv3EEVOzUtSLJsab5FOGSrqdVvhvO0Uczftlmu5WjESzcN9zaBkhfF8qp4gF7GoIHxe6eCvDwIDAQAB";

    private static final String PRV_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC+mdfhRB+yErnt5SZzDq/JKfFowEFveK1A44f5198FmRChVSPfDxwJHkg+/ULgl40YHpobUvkqYdMGHyBG67ksRQxx/NUCE2lDpMyclZpnEdx+xC9c+AdtEkd1TujXYH1hr7aVIOfsNof+4iKn2WPByRx81DAqRDILHGPPD4nEONUHE1BZE3DZAsUh3pgjW2KuWVLask0g+BqMFd2V2U9iQpEAsjTyoo/iG0LDh6sLbma82opZJeOT0+/XeWpXzHkor1gMOqLNehU+L0rOAyhDC0N4FOUYxaFH3uSpFmSrZeFZo7ERnh0qKgQlbHfsqQQmTpzVVVJxBsJpZywQyAlrAgMBAAECggEAaZUvUUo7hJl7BZ16vyHTnsvtPunBVc6zqs5f9LbwhFlWLQjM5jOohCSSQNJqyZ8qOcI5/Ajqnd+y1iFH8AGEglF9o2c7V/sIxL/12IfHEHGEg3SKCCYddv45N3KO6UA6NHLx4Ypo7yEGFNuP9wslh6OwdDXWuJMuZZlEcN9aoackjq7xTAcpwolz1kQAgp8GatlJp6ief5CLcDqDqPh4h+ln7f9WNxa7HE/F6jiHfKaetOwUQGuO+Sm24visrDnssVC3vXObnuZ/44PX+9jsX6UZG6xoiRqNDYE7wEH+SM8hFFB75TfkwnGbReGvm9pFHwoKyhPqTEPpXMDRtRh2gQKBgQDsdVN+ytgG/RvoAkBDkO7zWQ/fEhfxT8cvH94GH54wnE0o5E/Gr8TetQajV13u8eJ+bftq7ya/xqc1jsl8NY55AZwrb/B/tSnn9hthposGt8+AGw8/4FjZgiyKAgQY3OeQ35/UMKSJK/MJW4KaggzG8iE+lP7Gm6ey71gPTfP/TQKBgQDOWlPYXE3DX6im+C/wj/HX2jJnep/03O6AO8YzDRr0WS5/TPX55K2515hi/Zr/wh7WoEFUIMZHwmUjzHhPWriUGY3dMdDJRMBVYGvyLq8E025cH35Wp8Lvbgsx6YqT8D8QB3yViH5NhG6BcvJmoQj+29dTgP72kI+JGPUsZuq/lwKBgDcy8Gnh6mo/Pft8QwpC1DVErjrR23r+t63dj5P4X5JTeoO4QJNORvQCaGlLJOd52fjPreh5qZ8NPc135rszOW4wHQfa2tbFMOIwhpXWLhBEapEqP/YWZkdoZwNUkyZGs1wTiBkdvdszwkmF7kgpYdgzf+U/HPa6MXzI+NKhzPrlAoGAJoOlcpSHzxdjmwZnrDaxpOAM2FOC12kE+totPAoOJd0fWJM8tc+AvVcIEjHTZDkPGHAAwXr8hxg5WF0pd2YvXTUHzWkm1uoGG+lLPICKGhR8XKVkJzhYoeegH0i/nK0SmCNNlXtMV5uIJJEXvSABVdAieGtzKwIASE5oNElhNzECgYANS++4WsLbECWiof1rSc9wvdLye8EE7+SoSTjWSPdbJ30cnDO1jxZ8/Iw5WoUjPjJx+BFba9gSUzUhWL+/LncpXcPoxfuomh0r1ACm/jqq2Ril0X63s9TknWmHoQetK/lbrahe3cWKRivtdENREeDUkf+Q99l5yv8yXA0g06p2qw==";

    private static final String ALI_APP_ID = "2018121462561381";

    private static final String ALI_FACE_URL = "https://openapi.alipay.com/gateway.do";

    private static final String ALI_FACE_URL_DEV = "https://openapi.alipaydev.com/gateway.do";

    @RequestMapping(value="/faceVerifyResult", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject faceVerifyResult(@RequestBody JSONObject object){
        String zimId = object.getString("zimId");
        String bizId = object.getString("bizId");
        LOGGER.info("请求人脸识别验证开始,zimId:{},bizId:{}", zimId, bizId);

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(ALI_FACE_URL, ALI_APP_ID, PRV_KEY, "json", "GBK", PUB_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
        ZolozIdentificationCustomerCertifyzhubQueryRequest request = new ZolozIdentificationCustomerCertifyzhubQueryRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数
        //此次只是参数展示，未进行字符串转义，实际情况下请转义
        request.setBizContent("{" +
                "\"biz_id\":\""+bizId+"\"," +
                "\"zim_id\":\""+zimId+"\"," +
                "\"face_type\":\"2\"," +
                "\"bizType\":\"2\"" +
                "  }");

        ZolozIdentificationCustomerCertifyzhubQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            LOGGER.error("查询认证结果异常", e);
        }
        JSONObject retJson = new JSONObject();
        LOGGER.info(JSON.toJSONString(response));
        //调用成功，则处理业务逻辑
        if(response.isSuccess()){
            retJson.put("retCode", "0000");
            retJson.put("retMsg", "人脸认证成功");
        }else{
            //40004代表认证失败
            if(response.getCode().equals("40004")){
                //sub_code=INVALID_ARGUMENT 代表入參有误，更详细的原因请见 zim_code
                if(response.getSubCode().equals("INVALID_ARGUMENT")){
                    if(response.getZimCode().equals("Z5132")){
                        //zim_id 重复使用或已经过期
                        retJson.put("retCode", "-1");
                        retJson.put("retMsg", "人脸识别码重复使用或已经过期");
                    }else if(response.getZimCode().equals("Z5133")){
                        //biz_id 和 faceVerify 传入的bizId 不一致
                        retJson.put("retCode", "-2");
                        retJson.put("retMsg", "系统异常,请联系管理员.");
                    }else if(response.getSubCode().equals("Z5131")){
                        //传入的 zim_id 不正确
                        retJson.put("retCode", "-3");
                        retJson.put("retMsg", "系统异常,请联系管理员.");
                    }
                }else if(response.getSubCode().equals("SYSTEM_ERROR")){
                    //一般为支付宝系统问题，可以重新发起请求。
                    retJson.put("retCode", "-4");
                    retJson.put("retMsg", "支付宝人脸服务异常，请重新刷脸认证");
                }else if(response.getSubCode().equals("NOT_SAME_PERSON")){
                    //不是本人。
                    retJson.put("retCode", "-5");
                    retJson.put("retMsg", "不是本人");
                }else if(response.getSubCode().equals("UNABLE_GET_IMAGE")){
                    //公安网照片不可用
                    retJson.put("retCode", "-6");
                    retJson.put("retMsg", "您在公安户籍管理部门的证件照片不清晰，无法进行刷脸验证；请到户籍所在地派出所更新照片，更新的照片通常需要 1-3 个月生效。");
                }else if(response.getSubCode().equals("PROCESSING")){
                    //人脸比对流程没有结束
                    retJson.put("retCode", "-7");
                    retJson.put("retMsg", "支付宝人脸比对流程未结束，请重新刷脸认证");
                }else{
                    retJson.put("retCode", "-10001");
                    retJson.put("retMsg", "未知异常");
                }
            }else if(response.getCode().equals("40006")){
                retJson.put("retCode", "-8");
                retJson.put("retMsg", "系统异常，请联系管理员");
            }else {
                retJson.put("retCode", "-10001");
                retJson.put("retMsg", "未知异常");
            }
        }
        LOGGER.info("人脸识别返回信息,zimId:{}, bizId:{},信息:{}", zimId, bizId, JSON.toJSONString(retJson));
        return retJson;
    }

    public static void main(String[] args) {
        AliFaceVerify aliFaceVerify = new AliFaceVerify();
        //aliFaceVerify.faceVerifyResult("ab6cb3ef1b2ca4c64303b599736db9bdc45", "545689787654767653123xzm");
    }
}