package com.myblogbackend.blog.strategies;


import com.myblogbackend.blog.dtos.NotificationRequestDTO;
import com.myblogbackend.blog.enums.NotificationResult;

public interface NotificationStrategy {

    NotificationResult send(NotificationRequestDTO requestDTO);
}
