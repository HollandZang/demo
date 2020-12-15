package com.holland

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2
import com.holland.SpringApplication as MyApplication

@EnableSwagger2
@SpringBootApplication
open class SpringApplication

//fun main(args: Array<String>) {
//    SpringApplication.run(MyApplication::class.java, *args)
//}