package com.myblogbackend.blog.strategies;


import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailRegistrationConfirmationNotificationStrategy extends AbstractNotificationStrategy {

    private final EmailSendingService emailSendingService;
    private final NotificationMessageMapper notificationMessageMapper;
    private final EmailProperties emailProperties;


    @Override
    public NotificationResult handleNotifier(final NotificationRequestDTO message)
            throws MessagingException, TemplateException, IOException {
        var emailMessage = message.getEmail();
        var registrationConfirmationProperties = emailProperties.getRegistrationConfirmation();
        var simpleMailMessage =
                notificationMessageMapper.toSimpleMailMessage(registrationConfirmationProperties);
        // to end user
        simpleMailMessage.setTo(emailMessage.getTo());
        // build-in confirmation URL
        var confirmationURL = String.format(registrationConfirmationProperties.getBaseUrl(),
                emailMessage.getAdditionalProperties().getConfirmationToken());
        // bind confirmation URL to template
        Map<String, Object> dataBindings = Map.of(
                "confirmationToken", confirmationURL
        );

        var template = String.format(registrationConfirmationProperties.getTemplate());
        emailSendingService.send(template, simpleMailMessage, dataBindings);
        // send mail
        return NotificationResult.SUCCEEDED;
    }
}
