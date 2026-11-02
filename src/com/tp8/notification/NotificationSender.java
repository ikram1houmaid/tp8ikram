package com.tp8.notification;

public interface NotificationSender {

    void sendNotification(String recipient, String message);

    String getChannelType();
}