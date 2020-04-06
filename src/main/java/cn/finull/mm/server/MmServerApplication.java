package cn.finull.mm.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description
 * <p> 启动类
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
@EnableAsync
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class MmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmServerApplication.class, args);
    }
}
