package com.myblogbackend.blog.config.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
@AllArgsConstructor
@Getter
public class EmailProperties {

    private EmailInformation registrationConfirmation;


    @AllArgsConstructor
    @Getter
    public static class EmailInformation {

        private String template;
        private String subject;
        private String from;
        private String baseUrl;
    }
}
