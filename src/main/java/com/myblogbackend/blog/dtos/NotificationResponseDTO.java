package com.myblogbackend.blog.dtos;

import com.myblogbackend.blog.strategies.NotificationResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {

    private NotificationResponseCode code;
}
