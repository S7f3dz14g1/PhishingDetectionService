package com.szwedo.krystian.pds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PhishingDetectionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhishingDetectionServiceApplication.class, args);
	}

}
