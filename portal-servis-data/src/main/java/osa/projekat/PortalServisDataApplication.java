package osa.projekat;


import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import osa.projekat.config.FileStorageProperties;
import osa.projekat.service.storage.FileStorageService;


@Lazy
@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories("osa.projekat.repository")
@EnableJpaAuditing
@EnableConfigurationProperties(FileStorageProperties.class)
@EntityScan(basePackageClasses = { 
		PortalServisDataApplication.class,
		Jsr310JpaConverters.class 
})
public class PortalServisDataApplication {
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PortalServisDataApplication.class, args);
	}
	

}
