package com.myblogbackend.blog.strategies;


public interface NotificationStrategy {

    NotificationResult send(NotificationRequestDTO requestDTO);
}
