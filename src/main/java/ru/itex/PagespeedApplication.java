package ru.itex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@PropertySource("classpath:application.properties")
//@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories
public class PagespeedApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PagespeedApplication.class, args);
	}
        
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(PagespeedApplication.class);
        }
}
