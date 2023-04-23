package com.myblogbackend.blog.strategies;

import com.myblogbackend.blog.strategies.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {

    private NotificationType notificationType;

    private EmailMessageDTO email;
}
