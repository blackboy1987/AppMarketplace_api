package com.bootx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author black
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class YyscApiNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(YyscApiNewApplication.class, args);
    }

}
