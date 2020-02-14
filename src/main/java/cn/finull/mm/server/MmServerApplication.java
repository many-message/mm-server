package cn.finull.mm.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Description
 * <p> 启动类
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
@EnableCaching
@SpringBootApplication
public class MmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmServerApplication.class, args);
    }
}
