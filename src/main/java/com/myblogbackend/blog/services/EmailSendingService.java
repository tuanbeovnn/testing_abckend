package com.myblogbackend.blog.services;


import org.springframework.mail.SimpleMailMessage;

import java.util.Map;

public interface EmailSendingService {

    void send(String templateName, SimpleMailMessage simpleMailMessage, Map<String, Object> contentBindings);
}
