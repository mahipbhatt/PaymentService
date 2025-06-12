package com.example.paymentservice.service;

public interface PaymentService {
    public String initiatePayment(String orderId, String email,
                                  String phoneNumber, Long amount);
}
