package com.batchexample.first;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"config","listener","writer","reader","processor"})
public class DemoApplication {

	public static void main(String[] args) {

		System.out.print("hello world");
		SpringApplication.run(DemoApplication.class, args);
	}

}
