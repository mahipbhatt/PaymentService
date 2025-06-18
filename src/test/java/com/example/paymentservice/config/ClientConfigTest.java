package com.example.paymentservice.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link ClientConfig} class.
 * This class verifies the behavior of the Razorpay client creation logic.
 *
 * @author mahip.bhatt
 */
class ClientConfigTest {

    /**
     * Tests the creation of a RazorpayClient instance using the {@link ClientConfig#createRazorpayClient()} method.
     * Ensures that the RazorpayClient is successfully created and is not null.
     *
     * @throws RazorpayException if there is an error during Razorpay client creation
     */
    @Test
    void testCreateRazorpayClient() throws RazorpayException {
        // Arrange
        ClientConfig clientConfig = new ClientConfig();
        ReflectionTestUtils.setField(clientConfig, "razorpayClientKey", "testKey");
        ReflectionTestUtils.setField(clientConfig, "razorpayClientSecret", "testSecret");

        // Act
        RazorpayClient razorpayClient = clientConfig.createRazorpayClient();

        // Assert
        assertNotNull(razorpayClient, "RazorpayClient should not be null");
    }
}