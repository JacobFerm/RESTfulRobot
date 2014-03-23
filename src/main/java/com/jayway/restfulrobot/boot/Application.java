package com.jayway.restfulrobot.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@ComponentScan({"com.jayway.restfulrobot.domain",
        "com.jayway.restfulrobot.infra.rest"})
@EnableHypermediaSupport(type = HypermediaType.HAL)
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
