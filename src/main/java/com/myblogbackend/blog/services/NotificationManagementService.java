package com.myblogbackend.blog.services;


import com.myblogbackend.blog.dtos.NotificationRequestDTO;
import com.myblogbackend.blog.dtos.NotificationResponseDTO;

public interface NotificationManagementService {

    NotificationResponseDTO pushNotification(NotificationRequestDTO notificationRequestDTO);
}
