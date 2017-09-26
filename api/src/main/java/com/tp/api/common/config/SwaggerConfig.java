package com.tp.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by tupeng on 2017/8/3.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xcjy.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学成教育后台管理API文档")
                .description("该页面是学成教育系统接口文档，接口请求路径为：http://www.qianhengnet.com:8412/XXXX/... ，所有接口均可进行在线调试，所有数据均会影响到现有数据库。如有疑问可咨询相关开发人员：涂图")
                .termsOfServiceUrl("http://www.xcjy.com")
                .version("1.0-SNAPSHOT")
                .build();
    }

}
