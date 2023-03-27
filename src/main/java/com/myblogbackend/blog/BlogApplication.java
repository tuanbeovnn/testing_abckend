package com.myblogbackend.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@ConfigurationPropertiesScan(basePackages = {"com.myblogbackend.blog"})
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class BlogApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
