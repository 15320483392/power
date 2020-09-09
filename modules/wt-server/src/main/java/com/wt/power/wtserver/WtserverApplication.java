package com.wt.power.wtserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan({"com.wt.power.wtserver","com.wt.common.supervise"})
public class WtserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WtserverApplication.class, args);
	}

}
