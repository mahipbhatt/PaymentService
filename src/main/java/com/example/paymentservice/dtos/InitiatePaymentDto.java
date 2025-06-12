package com.example.paymentservice.dtos;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for initiating a payment.
 * Contains the necessary details required to process a payment.
 *
 * @author mahip.bhatt
 */
@Data
public class InitiatePaymentDto {

    /**
     * The email address of the user initiating the payment.
     */
    private String email;

    /**
     * The phone number of the user initiating the payment.
     */
    private String phoneNumber;

    /**
     * The amount to be paid, in the smallest currency unit (e.g., cents).
     */
    private Long amount;

    /**
     * The unique order ID associated with the payment.
     */
    private String orderId;
}