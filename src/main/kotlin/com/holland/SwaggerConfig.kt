package com.holland

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


@Configuration
//@EnableSwagger2
open class SwaggerConfig {
    @Bean
    open fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .pathMapping("/")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.holland.hadoop"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(ApiInfoBuilder()
                .title("后台接口")
                .description("后台接口")
                .version("1.0")
                .build())
    }
}
