package com.techmart.messaging; // Adjust package name if yours is different

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless
public class NotificationProducer {

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/OrderNotificationQueue")
    private Queue queue;

    private void sendMessage(String payload) {
        try {
            jmsContext.createProducer().send(queue, payload);
            System.out.println(">>> JMS Message Sent to Queue: " + payload);
        } catch (Exception e) {
            System.err.println(">>> Failed to send JMS message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendOrderSuccessMessage(Long orderId, Long userId) {
        sendMessage("SUCCESS," + orderId + "," + userId);
    }

    public void sendOrderStatusUpdate(Long orderId, Long userId, String newStatus) {
        sendMessage("UPDATE," + orderId + "," + userId + "," + newStatus);
    }
}