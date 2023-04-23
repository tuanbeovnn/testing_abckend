package com.myblogbackend.blog.strategies;


import com.myblogbackend.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthListener implements ApplicationListener<OnAuthListenerEvent> {

    private final UserService userService;
    private final NotificationManagementService notificationManagementService;

    private final PasswordEncoder encoder;

    @Override
    public void onApplicationEvent(final OnAuthListenerEvent event) {
        switch (event.getEventType()) {
            case "FORGOT":
//                this.sendPasswordForgot(event);
                break;
            case "REGISTER":
                this.confirmRegistration(event);
                break;
            default:
                break;
        }
    }

    private void confirmRegistration(final OnAuthListenerEvent event) {
        var userEntity = event.getUser();
        var token = UUID.randomUUID().toString();
        userService.createVerificationToken(userEntity, token, NotificationType.EMAIL_REGISTRATION_CONFIRMATION);

        var request = NotificationRequestDTO.builder()
                .notificationType(NotificationType.EMAIL_REGISTRATION_CONFIRMATION)
                .email(EmailMessageDTO.builder()
                        .to(userEntity.getEmail())
                        .additionalProperties(EmailAdditionalPropertiesDTO
                                .builder()
                                .confirmationToken(token)
                                .build())
                        .build())
                .build();
        notificationManagementService.pushNotification(request);
    }

}
