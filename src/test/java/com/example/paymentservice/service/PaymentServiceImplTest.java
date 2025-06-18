package com.example.paymentservice.service;

import com.example.paymentservice.paymentGateways.PaymentGateway;
import com.example.paymentservice.paymentGateways.PaymentGatewayStrategyChooser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link PaymentServiceImpl} class.
 * This class verifies the behavior of the payment initiation logic.
 * It ensures that the correct payment gateway is chosen and the payment link is generated successfully.
 *
 * @author mahip.bhatt
 */
class PaymentServiceImplTest {

    private PaymentServiceImpl paymentService;
    private PaymentGatewayStrategyChooser paymentGatewayStrategyChooser;
    private PaymentGateway paymentGateway;

    /**
     * Sets up the test environment by initializing the {@link PaymentServiceImpl} instance
     * and injecting the required dependencies using reflection.
     */
    @BeforeEach
    void setUp() {
        paymentGatewayStrategyChooser = mock(PaymentGatewayStrategyChooser.class);
        paymentGateway = mock(PaymentGateway.class);
        paymentService = new PaymentServiceImpl();
        ReflectionTestUtils.setField(paymentService, "paymentGatewayStrategyChooser", paymentGatewayStrategyChooser);
    }

    /**
     * Tests the successful initiation of a payment.
     * Verifies that the correct payment gateway is chosen and the payment link matches the expected value.
     */
    @Test
    void testInitiatePaymentSuccess() {
        // Arrange
        String orderId = "12345";
        String email = "test@example.com";
        String phoneNumber = "9876543210";
        Long amount = 1000L;
        String expectedPaymentLink = "https://example.com/payment-link";

        when(paymentGatewayStrategyChooser.getPaymentGateway()).thenReturn(paymentGateway);
        when(paymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount)).thenReturn(expectedPaymentLink);

        // Act
        String actualPaymentLink = paymentService.initiatePayment(orderId, email, phoneNumber, amount);

        // Assert
        assertEquals(expectedPaymentLink, actualPaymentLink, "Payment link should match the expected value");
        verify(paymentGatewayStrategyChooser, times(1)).getPaymentGateway();
        verify(paymentGateway, times(1)).generatePaymentLink(orderId, email, phoneNumber, amount);
    }
}