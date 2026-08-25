package com.tolmatchev.newsintelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsIntelligenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsIntelligenceApplication.class, args);
	}

}
