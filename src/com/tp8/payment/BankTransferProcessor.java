package com.tp8.payment;

public class BankTransferProcessor implements PaymentProcessor {

    private final String accountNumber;

    public BankTransferProcessor(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Erreur : Montant invalide pour le virement.");
            return false;
        }

        System.out.println("Virement de " + amount + "€ vers le compte " + accountNumber + " initié.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        System.out.println("Virement confirmé.");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Virement Bancaire";
    }
}