package com.myblogbackend.blog.strategies;



import org.mapstruct.Mapper;
import org.springframework.mail.SimpleMailMessage;

@Mapper(componentModel = "spring")
public interface NotificationMessageMapper {

    SimpleMailMessage toSimpleMailMessage(EmailProperties.EmailInformation emailProperties);
}
