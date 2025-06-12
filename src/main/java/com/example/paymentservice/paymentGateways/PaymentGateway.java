package com.example.paymentservice.paymentGateways;

/**
 * Interface for payment gateway integrations.
 * Provides a method to generate payment links for processing payments.
 *
 * @author mahip.bhatt
 */
public interface PaymentGateway {

    /**
     * Generates a payment link for the given order details.
     *
     * @param orderId the unique identifier for the order
     * @param email the email address of the user
     * @param phoneNumber the phone number of the user
     * @param amount the amount to be paid, in the smallest currency unit (e.g., cents)
     * @return a payment link as a {@link String}
     */
    String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount);
}