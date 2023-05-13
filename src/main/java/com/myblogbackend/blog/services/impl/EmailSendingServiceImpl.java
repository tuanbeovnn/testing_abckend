package com.myblogbackend.blog.services.impl;


import com.myblogbackend.blog.exception.commons.ErrorCode;
import com.myblogbackend.blog.exception.commons.BlogRuntimeException;
import com.myblogbackend.blog.services.EmailSendingService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSendingServiceImpl implements EmailSendingService {
    private Logger logger = LoggerFactory.getLogger(EmailSendingServiceImpl.class);
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerTemplateConfig;

    @Override
    @Async
    public void send(final String templateName,
                     final SimpleMailMessage simpleMailMessage,
                     final Map<String, Object> contentBindings) {

        try {
            var mimeMessage = javaMailSender.createMimeMessage();

            var mimeMessageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            var freemarkerTemplate = freemarkerTemplateConfig.getTemplate(templateName);
            var parsedHTML = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, contentBindings);

            mimeMessageHelper.setSubject(Objects.requireNonNullElse(simpleMailMessage.getSubject(), ""));
            mimeMessageHelper.setFrom(Objects.requireNonNull(simpleMailMessage.getFrom()));
            mimeMessageHelper.setTo(Objects.requireNonNull(simpleMailMessage.getTo()));
            mimeMessageHelper.setText(parsedHTML, true);

            javaMailSender.send(mimeMessage);
            logger.info("Email sent successfully.");
        } catch (MessagingException | TemplateException | IOException e) {
            throw new BlogRuntimeException(ErrorCode.ID_NOT_FOUND);
        }
    }
}
