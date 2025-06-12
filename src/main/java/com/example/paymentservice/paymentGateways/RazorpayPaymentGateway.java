package com.example.paymentservice.paymentGateways;

import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link PaymentGateway} interface for Razorpay.
 * Provides functionality to generate payment links using Razorpay's API.
 *
 * @author mahip.bhatt
 */
@Service
public class RazorpayPaymentGateway implements PaymentGateway {

    private final RazorpayClient razorpayClient;

    /**
     * Constructor-based dependency injection for {@link RazorpayClient}.
     *
     * @param razorpayClient the Razorpay client to interact with Razorpay's API
     */
    @Autowired
    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    /**
     * Generates a payment link for the given order details using Razorpay's API.
     *
     * @param orderId the unique identifier for the order
     * @param email the email address of the user
     * @param phoneNumber the phone number of the user
     * @param amount the amount to be paid, in the smallest currency unit (e.g., paise)
     * @return a payment link as a {@link String}
     * @throws RuntimeException if an error occurs while creating the payment link
     */
    @Override
    public String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "INR");
        paymentLinkRequest.put("accept_partial", true);
        paymentLinkRequest.put("first_min_partial_amount", 100);
        paymentLinkRequest.put("expire_by", 1775227432);
        paymentLinkRequest.put("reference_id", orderId);
        paymentLinkRequest.put("description", "Payment for order ID: " + orderId);

        JSONObject customer = new JSONObject();
        customer.put("name", phoneNumber);
        customer.put("contact", phoneNumber);
        customer.put("email", email);
        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("sms", true);
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put("reminder_enable", true);

        JSONObject notes = new JSONObject();
        notes.put("order_id", orderId);
        paymentLinkRequest.put("notes", notes);

        paymentLinkRequest.put("callback_url", "https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method", "get");

        try {
            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.toString();
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create payment link: " + e.getMessage(), e);
        }
    }
}