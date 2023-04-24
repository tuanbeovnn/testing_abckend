package com.myblogbackend.blog.services.impl;


import com.myblogbackend.blog.constant.ErrorMessage;
import com.myblogbackend.blog.exception.BlogLangException;
import com.myblogbackend.blog.services.NotificationManagementService;
import com.myblogbackend.blog.dtos.NotificationRequestDTO;
import com.myblogbackend.blog.strategies.NotificationResponseCode;
import com.myblogbackend.blog.dtos.NotificationResponseDTO;
import com.myblogbackend.blog.enums.NotificationResult;
import com.myblogbackend.blog.strategies.NotificationStrategyRegistry;
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
