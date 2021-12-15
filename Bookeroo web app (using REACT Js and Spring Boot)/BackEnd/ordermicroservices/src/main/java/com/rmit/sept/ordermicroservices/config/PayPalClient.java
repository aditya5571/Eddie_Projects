package com.rmit.sept.ordermicroservices.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PayPalClient {

    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;

    public PayPalHttpClient client() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
                clientId,
                clientSecret);
        return new PayPalHttpClient(environment);
    }
}