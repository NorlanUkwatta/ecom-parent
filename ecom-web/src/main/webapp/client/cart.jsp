<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.techmart.dto.CartItemDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Shopping Cart - TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="${pageContext.request.contextPath}/css/client.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<jsp:include page="components/navbar.jsp"/>

<div class="container py-5">
    <h2 class="fw-bold mb-4">Shopping Cart</h2>

    <%
        List<CartItemDTO> items = (List<CartItemDTO>) request.getAttribute("cartItems");
        Double subtotal = (Double) request.getAttribute("subtotal");

        if (items != null && !items.isEmpty()) {
    %>
    <div class="row g-4">
        <div class="col-lg-8">
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body p-4">
                    <% for (CartItemDTO item : items) { %>
                    <div class="row align-items-center mb-4 pb-4 border-bottom">
                        <div class="col-3 col-md-2">
                            <img src="${pageContext.request.contextPath}/images/<%= item.getImage1() != null ? item.getImage1() : "placeholder.jpg" %>"
                                 class="img-fluid rounded" alt="<%= item.getProductName() %>">
                        </div>
                        <div class="col-9 col-md-5 mb-3 mb-md-0">
                            <h6 class="fw-bold mb-1"><%= item.getProductName() %>
                            </h6>
                            <p class="text-muted small mb-0"><%= item.getBrandName() != null ? item.getBrandName() : "TechMart" %>
                            </p>

                            <form action="${pageContext.request.contextPath}/cart/remove" method="POST"
                                  class="d-inline">
                                <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                                <button type="submit"
                                        class="btn btn-link text-danger p-0 small text-decoration-none mt-2 d-inline-block shadow-none">
                                    <i class="bi bi-trash"></i> Remove
                                </button>
                            </form>

                        </div>
                        <div class="col-6 col-md-3">
                            <div class="d-flex align-items-center bg-light rounded-pill px-3 py-1"
                                 style="width: fit-content;">
                                <span class="text-muted small fw-bold me-2">QTY:</span>
                                <span class="fw-bold"><%= item.getQuantity() %></span>
                            </div>
                        </div>
                        <div class="col-6 col-md-2 text-end">
                            <h6 class="fw-bold text-brand mb-0">LKR <%= String.format("%.2f", item.getTotal()) %>
                            </h6>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card border-0 shadow-sm rounded-4 position-sticky" style="top: 100px;">
                <div class="card-body p-4">
                    <h5 class="fw-bold mb-4">Order Summary</h5>

                    <div class="d-flex justify-content-between mb-3">
                        <span class="text-muted">Subtotal</span>
                        <span class="fw-bold">LKR <%= String.format("%.2f", subtotal) %></span>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span class="text-muted">Shipping</span>
                        <span class="fw-medium text-success">Calculated at checkout</span>
                    </div>

                    <hr class="my-4">

                    <div class="d-flex justify-content-between mb-4">
                        <span class="fw-bold fs-5">Total</span>
                        <span class="fw-bold fs-5 text-brand">LKR <%= String.format("%.2f", subtotal) %></span>
                    </div>

                    <a href="${pageContext.request.contextPath}/checkout"
                       class="btn btn-brand btn-lg w-100 rounded-pill shadow-sm mb-3">
                        Proceed to Checkout <i class="bi bi-arrow-right ms-2"></i>
                    </a>

                    <div class="text-center">
                        <a href="${pageContext.request.contextPath}/shop" class="text-decoration-none small text-muted">
                            <i class="bi bi-arrow-left"></i> Continue Shopping
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <% } else { %>
    <div class="text-center py-5 bg-white rounded-4 shadow-sm">
        <i class="bi bi-cart-x display-1 text-muted opacity-25 mb-3 d-block"></i>
        <h3 class="fw-bold mb-3">Your cart is empty</h3>
        <p class="text-muted mb-4">Looks like you haven't added anything to your cart yet.</p>
        <a href="${pageContext.request.contextPath}/shop" class="btn btn-brand btn-lg rounded-pill px-5">Start
            Shopping</a>
    </div>
    <% } %>

</div>

<jsp:include page="components/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>