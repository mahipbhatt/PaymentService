package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.InitiatePaymentDto;
import com.example.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link PaymentController} class.
 * This class verifies the behavior of the payment initiation endpoint.
 *
 * @author mahip.bhatt
 */
class PaymentControllerTest {

    private MockMvc mockMvc;
    private PaymentService paymentService;

    /**
     * Sets up the test environment by initializing the {@link MockMvc} and mocking dependencies.
     */
    @BeforeEach
    void setUp() {
        paymentService = Mockito.mock(PaymentService.class);
        PaymentController paymentController = new PaymentController(paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    /**
     * Tests the payment initiation endpoint to ensure it returns the expected response.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void testInitiatePayment() throws Exception {
        // Arrange
        InitiatePaymentDto initiatePaymentDto = new InitiatePaymentDto();
        initiatePaymentDto.setOrderId("12345");
        initiatePaymentDto.setEmail("test@example.com");
        initiatePaymentDto.setPhoneNumber("9876543210");
        initiatePaymentDto.setAmount(1000L);

        String requestBody = """
                    {
                        "orderId": "12345",
                        "email": "test@example.com",
                        "phoneNumber": "9876543210",
                        "amount": 1000
                    }
                """;

        when(paymentService.initiatePayment("12345", "test@example.com", "9876543210", 1000L))
                .thenReturn("Payment initiated successfully");

        // Act & Assert
        mockMvc.perform(post("/payments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment initiated successfully"));

        verify(paymentService, times(1))
                .initiatePayment("12345", "test@example.com", "9876543210", 1000L);
    }
}