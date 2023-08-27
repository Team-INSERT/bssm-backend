package com.insert.ogbsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@ConfigurationPropertiesScan
@EnableJpaRepositories
@SpringBootApplication
public class OgBsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(OgBsmApplication.class, args);
	}

}
