package com.tp88;

import com.tp8.payment.*;
import com.tp8.notification.*;

public class Main {

    public static void main(String[] args) {

        // TEST PAIEMENT PAR CARTE
        PaymentProcessor cardProcessor =
                new CreditCardProcessor("4111-1111-1111-1111");

        PaymentService cardService =
                new PaymentService(cardProcessor);

        cardService.pay(50.0);

        System.out.println("\n" + "=".repeat(40) + "\n");

        // TEST PAIEMENT PAR VIREMENT
        PaymentProcessor transferProcessor =
                new BankTransferProcessor("FR76-3000-6000-0112-3456");

        PaymentService transferService =
                new PaymentService(transferProcessor);

        transferService.pay(150.0);

        System.out.println("\n" + "=".repeat(40) + "\n");

        // TEST NOTIFICATION EMAIL
        System.out.println("TESTS NOTIFICATIONS\n");

        NotificationSender emailSender =
                new EmailNotificationSender();

        NotificationService emailService =
                new NotificationService(emailSender);

        emailService.sendWelcomeMessage("user@example.com");

        System.out.println("\n");

        // TEST NOTIFICATION SMS
        NotificationSender smsSender =
                new SmsNotificationSender();

        NotificationService smsService =
                new NotificationService(smsSender);

        smsService.sendWelcomeMessage("+33612345678");
    }
}