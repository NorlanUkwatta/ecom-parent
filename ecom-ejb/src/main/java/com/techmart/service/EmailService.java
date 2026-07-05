package com.techmart.service;

import jakarta.ejb.Local;
import jakarta.ejb.Remote;

@Local
public interface EmailService {
    void sendOrderConfirmation(String toEmail, String customerName, Long orderId);
}
