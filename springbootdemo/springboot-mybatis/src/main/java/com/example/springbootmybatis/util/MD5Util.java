package com.example.springbootmybatis.util;


import org.apache.commons.codec.digest.DigestUtils;

import javax.management.MBeanAttributeInfo;

public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt="abcdefg";
    public static String inputPassToFromPass(String inputPass){
        String str=salt.charAt(0)+salt.charAt(1)+inputPass+salt.charAt(2);
        return md5(str);

    }
    public static String formPassToDBPass(String formPass,String salt){
        String str=salt.charAt(0)+salt.charAt(1)+formPass;
        return md5(str);


    }
    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass=inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(formPassToDBPass("123456789","123456789"));
    }

}
