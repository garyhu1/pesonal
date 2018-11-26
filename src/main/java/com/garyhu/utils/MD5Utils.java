package com.garyhu.utils;

import java.security.MessageDigest;

/**
 * @author: garyhu
 * @since: 2018/11/26 0026
 * @decription:
 */
public class MD5Utils {

    public static String getMD5(String inStr){

        MessageDigest md5 = null;

        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

        char[] chars = inStr.toCharArray();
        byte[] bytes = new byte[chars.length];

        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }

        byte[] md5Bytes = md5.digest(bytes);

        StringBuilder hexValue = new StringBuilder();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val  = ((int)md5Bytes[i]) & 0xff;
            if(val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
