package com.taoboot.mini.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil
{
    private static final byte[] IV = { 1, 2, 3, 4, 5, 6, 7, 8 };
    public static final String BM = "UTF-8";

    public static String encrypt(String encryptString, String encryptKey)
            throws Exception
    {
        IvParameterSpec zeroIv = new IvParameterSpec(IV);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(1, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
        return Base64.encode(encryptedData);
    }

    public static String decrypt(String decryptString, String decryptKey)
            throws Exception
    {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(IV);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(2, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData, "UTF-8");
    }

    public static void main(String[] args)
            throws Exception
    {
        String appId = "1D88AJ54P0000F34A8C00000C1055B48";
        String appSecret = "12BF692861CC88A0129764D7B6F8D3985D3AFBCD7A1D1DEA625548849A9919CD";
        String encData = "{\"name\":\"陈涛\",\"idNo\":\"350802199402157017\"}";

        String newPassword = encrypt(appSecret, appId.substring(0, 8));
        System.out.println(encrypt(encData, newPassword.substring(0, 8)));
    }
}
