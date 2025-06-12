package com.example.paymentservice.service;

import com.example.paymentservice.paymentGateways.PaymentGateway;
import com.example.paymentservice.paymentGateways.PaymentGatewayStrategyChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentGatewayStrategyChooser paymentGatewayStrategyChooser;

    @Override
    public String initiatePayment(String orderId, String email,
                                  String phoneNumber, Long amount) {
        PaymentGateway paymentGateway = paymentGatewayStrategyChooser.getPaymentGateway();
        return paymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount);
    }
}
