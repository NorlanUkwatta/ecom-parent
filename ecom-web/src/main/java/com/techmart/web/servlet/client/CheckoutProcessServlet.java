package com.techmart.web.servlet.client;

import com.techmart.core.util.SecurityUtil;
import com.techmart.dto.CartDTO;
import com.techmart.entity.Address;
import com.techmart.entity.Order;
import com.techmart.entity.User;
import com.techmart.service.CartService;
import com.techmart.service.OrderService; // You will create this EJB next
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "CheckoutProcessServlet", urlPatterns = {"/checkout/process"})
public class CheckoutProcessServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @EJB
    private CartService cartService;

    private final String MERCHANT_ID = "1231520";
    private final String MERCHANT_SECRET = "MTEwOTk2Nzc2NTI4MzM5MjIwMzkyOTQyNzQxMzY3MzAwMzEzOTA3Mg==";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Address shippingAddress = new Address();
        shippingAddress.setFirstName(req.getParameter("firstName"));
        shippingAddress.setLastName(req.getParameter("lastName"));
        shippingAddress.setEmail(req.getParameter("email"));
        shippingAddress.setMobile(req.getParameter("mobile"));
        shippingAddress.setStreetAddress(req.getParameter("streetAddress"));
        shippingAddress.setCity(req.getParameter("city"));
        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setPostalCode(req.getParameter("postalCode"));
        shippingAddress.setUser(loggedUser); // Link to user for future reuse

       Order pendingOrder = orderService.createPendingOrder(loggedUser, shippingAddress);

        String hash = SecurityUtil.generatePayHereHash(
                MERCHANT_ID,
                pendingOrder.getId().toString(),
                pendingOrder.getTotalAmount(),
                "LKR",
                MERCHANT_SECRET
        );

        req.setAttribute("merchantId", MERCHANT_ID);
        req.setAttribute("orderId", pendingOrder.getId());
        req.setAttribute("amount", String.format(Locale.ENGLISH,"%.2f", pendingOrder.getTotalAmount()));
        req.setAttribute("hash", hash);
        req.setAttribute("firstName", shippingAddress.getFirstName());
        req.setAttribute("lastName", shippingAddress.getLastName());
        req.setAttribute("email", shippingAddress.getEmail());
        req.setAttribute("mobile", shippingAddress.getMobile());
        req.setAttribute("address", shippingAddress.getStreetAddress() + ", " + shippingAddress.getCity());

        session.setAttribute("cartCount", 0);

        req.getRequestDispatcher("/client/payhere_redirect.jsp").forward(req, resp);
    }
}