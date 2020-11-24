package com.holland

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
open class SpringApplication

fun main(args: Array<String>) {
    SpringApplication.run(com.holland.SpringApplication::class.java, *args)
}
