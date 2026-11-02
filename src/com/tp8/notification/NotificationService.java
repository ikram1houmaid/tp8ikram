package com.tp8.notification;

public class NotificationService {

    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void sendWelcomeMessage(String recipient) {

        String message = "Bienvenue ! Votre compte a été créé avec succès.";

        System.out.println("Envoi via canal : " + sender.getChannelType());

        sender.sendNotification(recipient, message);
    }
}