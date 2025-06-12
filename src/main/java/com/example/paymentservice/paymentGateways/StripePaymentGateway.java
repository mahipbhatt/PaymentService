package com.example.paymentservice.paymentGateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link PaymentGateway} interface for Stripe.
 * Provides functionality to generate payment links using Stripe's API.
 *
 * @author mahip.bhatt
 */
@Service
public class StripePaymentGateway implements PaymentGateway {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    /**
     * Generates a payment link for the given order details using Stripe's API.
     *
     * @param orderId the unique identifier for the order
     * @param email the email address of the user
     * @param phoneNumber the phone number of the user
     * @param amount the amount to be paid, in the smallest currency unit (e.g., cents)
     * @return a payment link as a {@link String}
     * @throws RuntimeException if an error occurs while creating the payment link
     */
    @Override
    public String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount) {
        Stripe.apiKey = stripeApiKey;

        try {
            Price price = createPrice(amount, "usd");
            PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                    .addLineItem(
                            PaymentLinkCreateParams.LineItem.builder()
                                    .setPrice(price.getId())
                                    .setQuantity(1L)
                                    .build()
                    )
                    .setAfterCompletion(
                            PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl("https://example.com/success")
                                            .build())
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .build()
                    )
                    .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create payment link: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a price object in Stripe for the given amount and currency.
     *
     * @param amount the amount to be charged, in the smallest currency unit (e.g., cents)
     * @param currency the currency code (e.g., "usd")
     * @return the created {@link Price} object
     * @throws RuntimeException if an error occurs while creating the price
     */
    private Price createPrice(Long amount, String currency) {
        try {
            PriceCreateParams params = PriceCreateParams.builder()
                    .setCurrency(currency)
                    .setUnitAmount(amount)
                    .setProductData(
                            PriceCreateParams.ProductData.builder()
                                    .setName("Order Payment")
                                    .build()
                    )
                    .build();

            return Price.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create price: " + e.getMessage(), e);
        }
    }
}