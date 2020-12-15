package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.create.entity")
@EnableTransactionManagement //开启事务注解
public class DalConfig {

}
