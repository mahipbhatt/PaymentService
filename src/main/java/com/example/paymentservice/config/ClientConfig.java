package com.example.paymentservice.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Razorpay client.
 * This class reads Razorpay credentials from application properties
 * and provides a configured {@link RazorpayClient} bean.
 *
 * @author mahip.bhatt
 */
@Configuration
public class ClientConfig {

    @Value("${razorpay.client.key}")
    private String razorpayClientKey;

    @Value("${razorpay.client.secret}")
    private String razorpayClientSecret;

    /**
     * Creates and configures a {@link RazorpayClient} bean.
     *
     * @return a configured instance of {@link RazorpayClient}
     * @throws RazorpayException if there is an error during client initialization
     */
    @Bean
    public RazorpayClient createRazorpayClient() throws RazorpayException {
        return new RazorpayClient(razorpayClientKey, razorpayClientSecret);
    }
}