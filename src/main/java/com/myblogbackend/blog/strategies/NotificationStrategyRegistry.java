package com.myblogbackend.blog.strategies;

import com.myblogbackend.blog.strategies.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationStrategyRegistry {

    private final EmailRegistrationConfirmationNotificationStrategy emailRegistrationConfirmationNotificationStrategy;


    private final Map<NotificationType, NotificationStrategy>
            postNotificationStrategyMap = new EnumMap<>(NotificationType.class);

    @PostConstruct
    private void init() {
        postNotificationStrategyMap.put(NotificationType.EMAIL_REGISTRATION_CONFIRMATION, emailRegistrationConfirmationNotificationStrategy);
    }

    public NotificationStrategy findNotificationStrategy(final NotificationType notificationType) {
        return postNotificationStrategyMap.get(notificationType);
    }

}
