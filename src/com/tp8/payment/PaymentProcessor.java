package com.tp8.payment;

public interface PaymentProcessor {
    boolean processPayment(double amount);
    String getPaymentMethod();
}