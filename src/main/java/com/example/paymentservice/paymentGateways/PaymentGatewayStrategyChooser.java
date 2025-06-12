package com.example.paymentservice.paymentGateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for choosing the appropriate payment gateway strategy.
 * This class provides a method to select a payment gateway based on the business logic.
 *
 * @author mahip.bhatt
 */
@Service
public class PaymentGatewayStrategyChooser {

    private final RazorpayPaymentGateway razorpayPaymentGateway;
    private final StripePaymentGateway stripePaymentGateway;

    /**
     * Constructor-based dependency injection for payment gateways.
     *
     * @param razorpayPaymentGateway the Razorpay payment gateway implementation
     * @param stripePaymentGateway the Stripe payment gateway implementation
     */
    @Autowired
    public PaymentGatewayStrategyChooser(RazorpayPaymentGateway razorpayPaymentGateway, StripePaymentGateway stripePaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    /**
     * Chooses and returns the appropriate payment gateway.
     * Currently, it returns the Stripe payment gateway.
     *
     * @return the selected {@link PaymentGateway} implementation
     */
    public PaymentGateway getPaymentGateway() {
        return stripePaymentGateway;
    }
}