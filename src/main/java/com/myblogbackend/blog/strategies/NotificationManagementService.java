package com.myblogbackend.blog.strategies;




public interface NotificationManagementService {

    NotificationResponseDTO pushNotification(NotificationRequestDTO notificationRequestDTO);
}
