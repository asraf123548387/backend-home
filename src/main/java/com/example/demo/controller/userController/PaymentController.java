package com.example.demo.controller.userController;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentRetrieveParams;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final String stripeSecretKey = "sk_test_51Ok3CgSAIRBLhMSt0HuvraER5naNtWmtZHXCa4bYnmZsIZCwAj7kt6dyDvuL4NLak0zxWaR1RAu7XgPiH9dtxPUG009YNbgsfM";

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestBody Map<String, Object> requestData) {
        // Initialize Stripe with your API key
        Stripe.apiKey = "sk_test_51Ok3CgSAIRBLhMSt0HuvraER5naNtWmtZHXCa4bYnmZsIZCwAj7kt6dyDvuL4NLak0zxWaR1RAu7XgPiH9dtxPUG009YNbgsfM"; // Replace with your actual secret key

        try {
            Long amountLong = ((Number) requestData.get("amount")).longValue();

            // Create a new Checkout Session
            SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
            builder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
            builder.setMode(SessionCreateParams.Mode.PAYMENT);
            builder.setSuccessUrl("http://localhost:3000/?session_id={CHECKOUT_SESSION_ID}");
            builder.setCancelUrl("http://localhost:3000/login");
            builder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount(amountLong*100)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Room Price")
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );

            SessionCreateParams params = builder.build();
            Session session = Session.create(params);
            System.out.println(session.getId());
            // Return the Checkout Session ID to the client
            return ResponseEntity.ok().body(Map.of("sessionId", session.getId()));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Checkout Session");
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> requestBody) {
        String sessionId = requestBody.get("sessionId");
        Stripe.apiKey = stripeSecretKey;
        Map<String, Object> response = new HashMap<>();

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(
                    String.valueOf(PaymentIntentRetrieveParams.builder().setClientSecret(sessionId).build())
            );

            // Check if the payment was successful
            if ("succeeded".equals(paymentIntent.getStatus())) {
                response.put("status", "success");
                response.put("message", "Payment verified successfully.");
            } else {
                response.put("status", "failed");
                response.put("message", "Payment verification failed.");
            }
        } catch (StripeException e) {
            response.put("status", "error");
            response.put("message", "Error verifying payment: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

}
