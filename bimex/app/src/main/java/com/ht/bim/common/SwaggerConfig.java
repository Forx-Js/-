package com.ht.bim.common;

import ht.appfx.common.interceptor.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zhong on 2017/12/9.
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(LoginUser.class)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("ht.*"))
                .apis(RequestHandlerSelectors.basePackage("com.ht.bim.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("小明", "http://localhost/", "xiaoming@124.com");
        return new ApiInfoBuilder()
                .title("移动端API接口")
                .description("移动端API接口")
                .contact(contact)
                .version("1.0")
                .build();
    }
}