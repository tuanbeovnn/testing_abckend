package com.myblogbackend.blog.event;


import com.myblogbackend.blog.models.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class OnAuthListenerEvent extends ApplicationEvent {

    private String appUrl;

    private UserEntity user;

    private String eventType;

    private List<String> emails;

    private Object object;

    public OnAuthListenerEvent(
            final UserEntity user, final String appUrl, final String eventType, final Object... objects) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
        this.eventType = eventType;
        this.object = objects;
    }

}
