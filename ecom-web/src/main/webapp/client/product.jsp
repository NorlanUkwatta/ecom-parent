<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.techmart.entity.Product" %>
<%
    Product p = (Product) request.getAttribute("product");
    // Safety check just in case
    if (p == null) { response.sendRedirect(request.getContextPath() + "/shop"); return; }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= p.getName() %> - TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="${pageContext.request.contextPath}/css/client.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="components/navbar.jsp"/>

<div class="container py-5">

    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb small">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none text-muted">Home</a></li>
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/shop" class="text-decoration-none text-muted">Shop</a></li>
            <li class="breadcrumb-item active fw-bold text-dark" aria-current="page"><%= p.getName() %></li>
        </ol>
    </nav>

    <div class="row g-5">
        <div class="col-lg-6">
            <div id="productCarousel" class="carousel slide bg-white rounded-4 shadow-sm p-4 h-100 d-flex align-items-center justify-content-center" data-bs-ride="carousel">
                <div class="carousel-inner text-center">

                    <div class="carousel-item active">
                        <img src="${pageContext.request.contextPath}/images/<%= p.getImage1() != null ? p.getImage1() : "placeholder.jpg" %>" class="img-fluid product-detail-img" alt="Main Image">
                    </div>

                    <% if (p.getImage2() != null && !p.getImage2().isEmpty() && !p.getImage2().equals("null")) { %>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/<%= p.getImage2() %>" class="img-fluid product-detail-img" alt="Image 2">
                    </div>
                    <% } %>

                    <% if (p.getImage3() != null && !p.getImage3().isEmpty() && !p.getImage3().equals("null")) { %>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/<%= p.getImage3() %>" class="img-fluid product-detail-img" alt="Image 3">
                    </div>
                    <% } %>

                    <% if (p.getImage4() != null && !p.getImage4().isEmpty() && !p.getImage4().equals("null")) { %>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/images/<%= p.getImage4() %>" class="img-fluid product-detail-img" alt="Image 4">
                    </div>
                    <% } %>
                </div>

                <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon rounded-circle bg-dark p-3" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon rounded-circle bg-dark p-3" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>

        <div class="col-lg-6">

            <span class="badge category-pill text-uppercase px-3 py-2 mb-3">
                <%= p.getBrand() != null ? p.getBrand().getName() : "Premium Tech" %>
            </span>
            <h1 class="fw-bold mb-3 display-6"><%= p.getName() %></h1>

            <h2 class="text-brand fw-bold mb-4">LKR <%= String.format("%.2f", p.getPrice()) %></h2>

            <p class="text-muted lead mb-4"><%= p.getDescription() != null ? p.getDescription() : "No description available." %></p>

            <div class="row g-3 mb-4 pb-4 border-bottom">
                <div class="col-sm-6">
                    <div class="p-3 bg-white rounded-3 shadow-sm border border-light">
                        <span class="d-block text-muted small fw-bold text-uppercase mb-1">Category</span>
                        <span class="fw-medium text-dark"><%= p.getCategory() != null ? p.getCategory().getName() : "N/A" %></span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="p-3 bg-white rounded-3 shadow-sm border border-light">
                        <span class="d-block text-muted small fw-bold text-uppercase mb-1">Storage</span>
                        <span class="fw-medium text-dark"><%= p.getStorage() != null ? p.getStorage().getCapacity() : "N/A" %></span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="p-3 bg-white rounded-3 shadow-sm border border-light">
                        <span class="d-block text-muted small fw-bold text-uppercase mb-1">Color</span>
                        <span class="fw-medium text-dark"><%= p.getColor() != null ? p.getColor().getName() : "N/A" %></span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="p-3 bg-white rounded-3 shadow-sm border border-light">
                        <span class="d-block text-muted small fw-bold text-uppercase mb-1">Availability</span>
                        <% if(p.getStockQuantity() > 0) { %>
                        <span class="fw-bold text-success"><i class="bi bi-check-circle-fill me-1"></i> In Stock (<%= p.getStockQuantity() %>)</span>
                        <% } else { %>
                        <span class="fw-bold text-danger"><i class="bi bi-x-circle-fill me-1"></i> Out of Stock</span>
                        <% } %>
                    </div>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/cart/add" method="POST" class="d-flex align-items-end gap-3 mt-4">
                <input type="hidden" name="productId" value="<%= p.getId() %>">

                <div style="width: 120px;">
                    <label class="form-label fw-bold text-muted small text-uppercase">Quantity</label>
                    <input type="number" class="form-control form-control-lg fw-bold text-center" name="quantity" value="1" min="1" max="<%= p.getStockQuantity() > 0 ? p.getStockQuantity() : 1 %>" <%= p.getStockQuantity() == 0 ? "disabled" : "" %>>
                </div>

                <button type="submit" class="btn btn-brand btn-lg flex-grow-1 shadow rounded-3" <%= p.getStockQuantity() == 0 ? "disabled" : "" %>>
                    <i class="bi bi-cart-plus me-2"></i> Add to Cart
                </button>
            </form>

        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>