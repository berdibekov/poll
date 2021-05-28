package com.berdibekov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("auth")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.berdibekov.api.controller.auth"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Ilyas Berdibekov Poll API for authenticated users",
                "This is test task for Fabrique.studio",
                "V1",
                "Terms of service",
                new Contact("Ilyas Berdibekov", "www.ilyas.com", "mr.berdibekov@bk.ru"),
                "License of API", "API license URL", Collections.emptyList());
    }

    @Bean
    public Docket guestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("guest")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.berdibekov.api.controller.guest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(guestApiInfo());
    }

    private ApiInfo guestApiInfo() {
        return new ApiInfo(
                "Ilyas Berdibekov Poll API for guests",
                "This is test task for Fabrique.studio",
                "V1",
                "Terms of service",
                new Contact("Ilyas Berdibekov", "www.ilyas.com", "mr.berdibekov@bk.ru"),
                "License of API", "API license URL", Collections.emptyList());
    }
}