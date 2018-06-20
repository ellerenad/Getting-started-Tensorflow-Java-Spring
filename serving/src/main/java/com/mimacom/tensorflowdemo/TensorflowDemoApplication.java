package com.mimacom.tensorflowdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.mimacom.irisml"})
@SpringBootApplication
public class TensorflowDemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TensorflowDemoApplication.class, args);
    }
}
