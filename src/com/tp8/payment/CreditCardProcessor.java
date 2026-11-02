package com.tp8.payment;

public class CreditCardProcessor implements PaymentProcessor {

    private final String cardNumber;

    public CreditCardProcessor(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Erreur : Montant invalide pour la carte " + cardNumber);
            return false;
        }

        System.out.println("Paiement de " + amount + "€ par carte " + cardNumber + " accepté.");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Carte Bancaire";
    }
}