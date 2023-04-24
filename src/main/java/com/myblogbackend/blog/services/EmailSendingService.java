package com.myblogbackend.blog.services;


import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;
import java.util.Map;

public interface EmailSendingService {

    void send(String templateName, SimpleMailMessage simpleMailMessage, Map<String, Object> contentBindings)
            throws MessagingException, TemplateException, IOException;
}
