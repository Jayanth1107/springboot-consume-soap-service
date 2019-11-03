package com.jayanth.springbootconsumesoapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.jayanth.springbootconsumesoapservice"})
public class SpringbootConsumeSoapServiceApplication{
	public static void main(String[] args) {
		SpringApplication.run(SpringbootConsumeSoapServiceApplication.class, args);
	}
}
