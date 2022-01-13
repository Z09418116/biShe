package com.example.bishe.test;

import com.example.bishe.shiro.MyByteSource;
import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {
    public static void main(String[] args) {
        MyByteSource myByteSource = new MyByteSource("ainjee");
        System.out.println(myByteSource.toHex());
        //打印出 经过 md5hash 加密后的密码, "1" 代表密码， salt 代表给密码加的随机字符串盐,
        //hashIterations 代表散列的次数
        Md5Hash md5Hash = new Md5Hash("1","ainjee",6);
        System.out.println(md5Hash.toHex());
    }
}
