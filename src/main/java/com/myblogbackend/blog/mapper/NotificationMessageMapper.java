package com.myblogbackend.blog.mapper;



import com.myblogbackend.blog.config.mail.EmailProperties;
import org.mapstruct.Mapper;
import org.springframework.mail.SimpleMailMessage;

@Mapper(componentModel = "spring")
public interface NotificationMessageMapper {

    SimpleMailMessage toSimpleMailMessage(EmailProperties.EmailInformation emailProperties);
}
