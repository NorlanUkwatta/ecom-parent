package com.techmart.web.servlet.client;

import com.techmart.core.util.SecurityUtil;
import com.techmart.entity.Order;
import com.techmart.messaging.NotificationProducer;
import com.techmart.service.OrderService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "PayHereNotifyServlet", urlPatterns = {"/payhere/notify"})
public class PayHereNotifyServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @EJB
    private NotificationProducer  notificationProducer;

    private final String MERCHANT_ID = "1231520";
    private final String MERCHANT_SECRET = "MTEwOTk2Nzc2NTI4MzM5MjIwMzkyOTQyNzQxMzY3MzAwMzEzOTA3Mg==";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String merchantId = req.getParameter("merchant_id");
        String orderId = req.getParameter("order_id");
        String payhereAmount = req.getParameter("payhere_amount");
        String payhereCurrency = req.getParameter("payhere_currency");
        String statusCode = req.getParameter("status_code");
        String md5sig = req.getParameter("md5sig");

        boolean isValid = SecurityUtil.verifyPayHereHash(
                merchantId, orderId, payhereAmount, payhereCurrency, statusCode, md5sig, MERCHANT_SECRET
        );

        if (isValid && statusCode.equals("2")) {
            System.out.println("Payment Successful for Order: " + orderId);

            orderService.updateOrderStatus(Long.parseLong(orderId), "PAID");

            Long parsedOrderId = Long.parseLong(orderId);
            Long customerId = orderService.getUserIdByOrderId(parsedOrderId);

            if (customerId != null) {
                notificationProducer.sendOrderSuccessMessage(parsedOrderId, customerId);
            } else {
                System.err.println("Webhook Success, but failed to find User ID for notification.");
            }

        } else {
            System.err.println("Invalid Payment or Fake Request for Order: " + orderId);
        }

        resp.setStatus(200);
    }
}
