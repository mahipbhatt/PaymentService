package com.example.paymentservice.paymentGateways;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link StripePaymentGateway} class.
 * This class verifies the behavior of the Stripe payment gateway integration.
 *
 * @author mahip.bhatt
 */
class StripePaymentGatewayTest {

    private StripePaymentGateway stripePaymentGateway;

    /**
     * Sets up the test environment by initializing the {@link StripePaymentGateway} instance
     * and injecting the required fields using reflection.
     */
    @BeforeEach
    void setUp() {
        stripePaymentGateway = new StripePaymentGateway();
        ReflectionTestUtils.setField(stripePaymentGateway, "stripeApiKey", "test_api_key");
    }

    /**
     * Tests the successful generation of a payment link using the Stripe API.
     * Verifies that the payment link is created and matches the expected value.
     *
     * @throws StripeException if there is an error during the Stripe API call
     */
    @Test
    void testGeneratePaymentLinkSuccess() throws StripeException {
        // Arrange
        String orderId = "12345";
        String email = "test@example.com";
        String phoneNumber = "9876543210";
        Long amount = 1000L;

        PaymentLink paymentLink = mock(PaymentLink.class);
        when(paymentLink.getUrl()).thenReturn("https://stripe.com/payment-link");

        Price price = mock(Price.class);
        when(price.getId()).thenReturn("price_123");

        try (MockedStatic<PaymentLink> mockedPaymentLink = mockStatic(PaymentLink.class);
             MockedStatic<Price> mockedPrice = mockStatic(Price.class)) {
            mockedPrice.when(() -> Price.create(any(PriceCreateParams.class))).thenReturn(price);
            mockedPaymentLink.when(() -> PaymentLink.create(any(PaymentLinkCreateParams.class))).thenReturn(paymentLink);

            // Act
            String result = stripePaymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount);

            // Assert
            assertNotNull(result, "Payment link should not be null");
            assertEquals("https://stripe.com/payment-link", result, "Payment link should match the expected value");
            mockedPrice.verify(() -> Price.create(any(PriceCreateParams.class)), times(1));
            mockedPaymentLink.verify(() -> PaymentLink.create(any(PaymentLinkCreateParams.class)), times(1));
        }
    }
}