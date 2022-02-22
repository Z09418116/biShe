package com.example.bishe.config.base;

import java.util.Random;

public class RandomString {

    public static String getRandomString(Integer index){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        //指定字符串长度，拼接字符并toString
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < index; i++) {
            //获取指定长度的字符串中任意一个字符的索引值
            int number=random.nextInt(str.length());
            //根据索引值获取对应的字符
            char charAt = str.charAt(number);
            sb.append(charAt);
        }
        return sb.toString();
    }

    public static String getRandomJobNumber(String maxJobNumber){
        Integer int1 = Integer.parseInt(maxJobNumber);
        int1 += 1;
        String s1 = int1.toString();

        return s1;
    }

}
