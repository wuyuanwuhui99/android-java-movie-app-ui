package com.player.movie.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * 获取字符串对应的MD5
     * @param str
     * @return
     */
    public static String getStrMD5(String str) {
        String ret="";
        if (!TextUtils.isEmpty(str)) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] charArray = str.toCharArray();
                byte[] byteArray = new byte[charArray.length];
                for (int i = 0; i < charArray.length; i++) {
                    byteArray[i] = (byte) charArray[i];
                }
                byte[] md5Bytes = md5.digest(byteArray);
                StringBuilder hexValue = new StringBuilder();
                for (int i = 0; i < md5Bytes.length; i++) {
                    int val = ((int) md5Bytes[i]) & 0xff;
                    if (val < 16) {
                        hexValue.append("0");
                    }
                    hexValue.append(Integer.toHexString(val));
                }
                ret= hexValue.toString().toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}

