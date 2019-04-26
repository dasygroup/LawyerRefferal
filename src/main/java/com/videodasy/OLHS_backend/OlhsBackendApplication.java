package com.videodasy.OLHS_backend;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@Configuration
@EnableJpaAuditing
public class OlhsBackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OlhsBackendApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OlhsBackendApplication.class); 
	}
	
	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory h){
		return h.getSessionFactory();
		
	}
	

}
