package com.myblogbackend.blog.strategies;


import com.myblogbackend.blog.dtos.NotificationRequestDTO;
import com.myblogbackend.blog.enums.NotificationResult;
import com.myblogbackend.blog.exception.commons.ErrorCode;
import com.myblogbackend.blog.exception.commons.BlogRuntimeException;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public abstract class AbstractNotificationStrategy implements NotificationStrategy {

    @Override
    public NotificationResult send(final NotificationRequestDTO requestDTO) {
        try {
            return handleNotifier(requestDTO);
        } catch (Exception exception) {
            throw new BlogRuntimeException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    public abstract NotificationResult handleNotifier(final NotificationRequestDTO notificationRequest)
            throws MessagingException, TemplateException, IOException;
}
