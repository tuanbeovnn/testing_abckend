package com.myblogbackend.blog.event;

import com.myblogbackend.blog.cache.LoggedOutJwtTokenCache;
import com.myblogbackend.blog.request.DeviceInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;



@Component
public class OnUserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

    private final LoggedOutJwtTokenCache tokenCache;
    private static final Logger logger = LoggerFactory.getLogger(OnUserLogoutSuccessEventListener.class);

    @Autowired
    public OnUserLogoutSuccessEventListener(LoggedOutJwtTokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
        if (null != event) {
            DeviceInfoRequest deviceInfoRequest = event.getLogOutRequest().getDeviceInfo();
            logger.info(String.format("Log out success event received for user [%s] for device [%s]", event.getUserEmail(), deviceInfoRequest));
            tokenCache.markLogoutEventForToken(event);
        }
    }
}