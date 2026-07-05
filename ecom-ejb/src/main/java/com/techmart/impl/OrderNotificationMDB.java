package com.techmart.impl;

import com.techmart.core.enums.Role;
import com.techmart.entity.Address;
import com.techmart.entity.Notification;
import com.techmart.entity.User;
import com.techmart.service.EmailService;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/OrderNotificationQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class OrderNotificationMDB implements MessageListener {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @EJB
    private EmailService emailService;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String payload = ((TextMessage) message).getText();
                System.out.println("<<< JMS Message Received: " + payload);

                String[] data = payload.split(",");
                String messageType = data[0]; // "SUCCESS" or "UPDATE"
                Long orderId = Long.parseLong(data[1]); // Shifted to index 1
                Long userId = Long.parseLong(data[2]);  // Shifted to index 2

                User customer = em.find(User.class, userId);

                if (customer != null) {

                    // Fetch the Address linked to this User
                    Address shippingAddress = null;
                    try {
                        shippingAddress = em.createQuery(
                                        "SELECT a FROM Address a WHERE a.user = :user ORDER BY a.id DESC", Address.class)
                                .setParameter("user", customer)
                                .setMaxResults(1)
                                .getSingleResult();
                    } catch (Exception ex) {
                        System.err.println(">>> Warning: No address found for User ID " + userId);
                    }

                    // ROUTE 1: BRAND NEW ORDER
                    if ("SUCCESS".equals(messageType)) {

                        Notification customerNotif = new Notification();
                        customerNotif.setTitle("Order Confirmed!");
                        customerNotif.setMessage("Your payment for Order #" + orderId + " was successful. Thank you for shopping with us.");
                        customerNotif.setTargetRole(Role.CUSTOMER);
                        customerNotif.setTargetUser(customer);
                        em.persist(customerNotif);

                        Notification adminNotif = new Notification();
                        adminNotif.setTitle("New Order Received");
                        adminNotif.setMessage(customer.getUsername() + " placed a new order (Order #" + orderId + ").");
                        adminNotif.setTargetRole(Role.ADMIN);
                        adminNotif.setTargetUser(null);
                        em.persist(adminNotif);

                        System.out.println(">>> Notifications successfully saved to database!");

                        if (shippingAddress != null) {
                            emailService.sendOrderConfirmation(
                                    shippingAddress.getEmail(),
                                    shippingAddress.getFirstName(),
                                    orderId
                            );
                        } else {
                            System.out.println(">>> Falling back to User account email...");
                            emailService.sendOrderConfirmation(
                                    customer.getEmail(),
                                    customer.getUsername(),
                                    orderId
                            );
                        }
                    }
                    // ROUTE 2: ADMIN CHANGED THE STATUS
                    else if ("UPDATE".equals(messageType)) {
                        String newStatus = data[3]; // The 4th item in the payload

                        // Create Status Update Notification for Customer
                        Notification statusNotif = new Notification();
                        statusNotif.setTitle("Order Update: " + newStatus);
                        statusNotif.setMessage("Your Order #" + orderId + " is now marked as " + newStatus + ".");
                        statusNotif.setTargetRole(Role.CUSTOMER);
                        statusNotif.setTargetUser(customer);
                        em.persist(statusNotif);

                        System.out.println(">>> Status Update Notification saved to database!");

                        // Note: You can add an emailService.sendOrderUpdate(...) call here later
                        // if you want to email them about shipping updates!
                    }

                } else {
                    System.err.println("Could not create notification or send email: User not found in DB.");
                }

            }
        } catch (Exception e) {
            System.err.println("Error processing JMS message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}