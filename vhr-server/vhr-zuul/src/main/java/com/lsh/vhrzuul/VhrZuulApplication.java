package com.lsh.vhrzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@EnableZuulProxy
public class VhrZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(VhrZuulApplication.class, args);
    }

}
