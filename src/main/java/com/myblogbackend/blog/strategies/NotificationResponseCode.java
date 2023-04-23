package com.myblogbackend.blog.strategies;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum NotificationResponseCode {
    NOTIFICATION_2000("Pushed Notification"),
    NOTIFICATION_4000(""),
    NOTIFICATION_4001("User Not Found"),
    NOTIFICATION_5000(""),
    NOTIFICATION_5999("Unknown Error"),
    ;

    private final String message;

    public static boolean isBizError(final NotificationResponseCode notificationResponseCode) {
        return BIZ_ERRORS.contains(notificationResponseCode);
    }

    public static boolean isClientError(final NotificationResponseCode notificationResponseCode) {
        return CLIENT_ERRORS.contains(notificationResponseCode);
    }

    public static boolean isServerError(final NotificationResponseCode notificationResponseCode) {
        return SERVER_ERRORS.contains(notificationResponseCode);
    }

    private static final Set<NotificationResponseCode> BIZ_ERRORS = Set.of(
            NOTIFICATION_2000
    );

    private static final Set<NotificationResponseCode> CLIENT_ERRORS = Set.of(
            NOTIFICATION_4000
    );

    private static final Set<NotificationResponseCode> SERVER_ERRORS = Set.of(
            NOTIFICATION_5000,
            NOTIFICATION_5999
    );
}
