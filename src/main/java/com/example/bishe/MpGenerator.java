package com.example.bishe;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MpGenerator {

    public static void main(String[] args) {

        // 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        // 配置策略
        //
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去Service的I前缀
        mpg.setGlobalConfig(gc);

        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/spring-security?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example.bishe");
//        pc.setEntity("");
//        pc.setMapper("");
//        pc.setService("service");
//        pc.setController("controller");
        mpg.setPackageInfo(pc);

        TemplateConfig tc = new TemplateConfig();

//        tc.setController("");
//        tc.setServiceImpl("impl");
//        tc.setEntity("");
//        tc.setMapper("");
//        tc.setXml("");
//        tc.setService("service");
        mpg.setTemplate(tc);

        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("annex","country","group","information","project","score_details","score_item","scores","tab","user","works"); // 设置要映射的表名
        strategy.setInclude(); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); // 自动lombok；
        strategy.setRestControllerStyle(true);
        mpg.setStrategy(strategy);

        mpg.execute(); //执行
    }
}
