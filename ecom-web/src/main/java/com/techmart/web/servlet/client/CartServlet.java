package com.techmart.web.servlet.client;

import com.techmart.dto.CartDTO;
import com.techmart.dto.CartItemDTO;
import com.techmart.entity.Cart;
import com.techmart.entity.CartItem;
import com.techmart.entity.Product;
import com.techmart.entity.User;
import com.techmart.service.CartService;
import com.techmart.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    @EJB
    private CartService cartService;

    @EJB
    private ProductService productService;

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        List<CartItemDTO> displayItems = new ArrayList<>();
        double subtotal = 0.0;

        if (loggedUser != null) {

            CartDTO cart = cartService.getCartByUser(loggedUser);
            if (cart != null && cart.getItems() != null) {
                displayItems = cart.getItems();
                for (CartItemDTO item : displayItems) {
                    subtotal += item.getTotal();
                }
            }
        } else {

            Map<Long, Integer> guestCart = (Map<Long, Integer>) session.getAttribute("guestCart");
            if (guestCart != null) {
                for (Map.Entry<Long, Integer> entry : guestCart.entrySet()) {
                    Product p = productService.getProductById(entry.getKey());
                    if (p != null) {
                        CartItemDTO dto = new CartItemDTO();
                        dto.setProductId(p.getId());
                        dto.setProductName(p.getName());
                        dto.setPrice(p.getPrice());
                        dto.setImage1(p.getImage1());
                        dto.setQuantity(entry.getValue());
                        if (p.getBrand() != null) {
                            dto.setBrandName(p.getBrand().getName());
                        }

                        displayItems.add(dto);
                        subtotal += dto.getTotal();
                    }
                }
            }
        }

        req.setAttribute("cartItems", displayItems);
        req.setAttribute("subtotal", subtotal);
        req.getRequestDispatcher("/client/cart.jsp").forward(req, resp);
    }

    public static class CartItemView {
        private Product product;
        private int quantity;

        public CartItemView(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotal() {
            return product.getPrice() * quantity;
        }
    }

}
