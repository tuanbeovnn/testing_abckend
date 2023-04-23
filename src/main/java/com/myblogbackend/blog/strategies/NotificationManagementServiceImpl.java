package com.myblogbackend.blog.strategies;


import com.myblogbackend.blog.constant.ErrorMessage;
import com.myblogbackend.blog.exception.BlogLangException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationManagementServiceImpl implements NotificationManagementService {

    private final NotificationStrategyRegistry notificationStrategyRegistry;

    @Override
    public NotificationResponseDTO pushNotification(final NotificationRequestDTO notificationRequestDTO) {
        try {
            var notificationResult = notificationStrategyRegistry
                    .findNotificationStrategy(notificationRequestDTO.getNotificationType())
                    .send(notificationRequestDTO);
            // push notification success
            if (NotificationResult.SUCCEEDED == notificationResult) {
                return NotificationResponseDTO.builder()
                        .code(NotificationResponseCode.NOTIFICATION_2000)
                        .build();
            }
            // push notification fail
            throw new BlogLangException(ErrorMessage.EMAIL_SEND_FAILED);
        } catch (BlogLangException notificationException) {
            log.debug("NOTIFICATION::EXCEPTION an exception was occurred ", notificationException);
            // handle by controller advice
            throw notificationException;
        }
    }
}
