package com.example.bishe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.bishe.mapper")
public class BiSheApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiSheApplication.class, args);
    }

}
