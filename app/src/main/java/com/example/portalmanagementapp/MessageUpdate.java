package com.example.portalmanagementapp;

public class MessageUpdate {
    private NotificationUpdate notificationUpdate;
    private String token;

    public MessageUpdate(NotificationUpdate notificationUpdate, String token) {
        this.notificationUpdate = notificationUpdate;
        this.token = token;
    }

    public NotificationUpdate getNotificationUpdate() {
        return notificationUpdate;
    }

    public void setNotificationUpdate(NotificationUpdate notificationUpdate) {
        this.notificationUpdate = notificationUpdate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
