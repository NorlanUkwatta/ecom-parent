<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.techmart.entity.Product" %>
<%@ page import="com.techmart.entity.User" %>
<%
    User admin = (User) session.getAttribute("loggedUser");
    if (admin == null || !admin.getRole().name().equals("ADMIN")) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Inventory - TechMart Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .table-hover tbody tr:hover { background-color: #f8f9fa; transition: 0.2s; }
        .card { border-radius: 0.75rem; }
    </style>
</head>
<body class="bg-light">

<jsp:include page="components/navbar.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="components/sidebar.jsp"/>

        <div class="col-md-10 p-4">

            <div class="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
                <div>
                    <h2 class="fw-bold mb-0">Inventory Management</h2>
                    <p class="text-muted small mb-0">Manage your products, stock, and variations.</p>
                </div>

                <div class="d-flex gap-3">
                    <div class="dropdown">
                        <button class="btn btn-white border shadow-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-gear me-1"></i> Configurations
                        </button>
                        <ul class="dropdown-menu shadow border-0">
                            <li><a class="dropdown-item py-2" href="#" data-bs-toggle="modal" data-bs-target="#addCategoryModal"><i class="bi bi-tags me-2 text-primary"></i> Manage Categories</a></li>
                            <li><a class="dropdown-item py-2" href="#" data-bs-toggle="modal" data-bs-target="#addColorModal"><i class="bi bi-palette me-2 text-danger"></i> Manage Colors</a></li>
                            <li><a class="dropdown-item py-2" href="#" data-bs-toggle="modal" data-bs-target="#addStorageModal"><i class="bi bi-hdd me-2 text-secondary"></i> Manage Storage</a></li>
                            <li><a class="dropdown-item py-2" href="#" data-bs-toggle="modal" data-bs-target="#addBrandModal"><i class="bi bi-award me-2 text-success"></i> Manage Brands</a></li>
                        </ul>
                    </div>

                    <button type="button" class="btn btn-primary shadow-sm px-4" data-bs-toggle="modal" data-bs-target="#addProductModal">
                        <i class="bi bi-plus-lg me-1"></i> Add Product
                    </button>
                </div>
            </div>

            <% if (request.getAttribute("successMessage") != null) { %>
            <div class="alert alert-success border-0 shadow-sm alert-dismissible fade show rounded-3" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i> <%= request.getAttribute("successMessage") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger border-0 shadow-sm alert-dismissible fade show rounded-3" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i> <%= request.getAttribute("errorMessage") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>

            <div class="card shadow-sm border-0 overflow-hidden">
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light border-bottom">
                        <tr>
                            <th class="px-4 py-3 text-muted small text-uppercase">ID</th>
                            <th class="py-3 text-muted small text-uppercase">Product Name</th>
                            <th class="py-3 text-muted small text-uppercase">Price</th>
                            <th class="py-3 text-muted small text-uppercase">Stock</th>
                            <th class="px-4 py-3 text-end text-muted small text-uppercase">Actions</th>
                        </tr>
                        </thead>
                        <tbody class="border-top-0">
                        <%
                            List<Product> products = (List<Product>) request.getAttribute("products");
                            if (products != null && !products.isEmpty()) {
                                for (Product p : products) {
                        %>
                        <tr>
                            <td class="px-4 text-muted">#<%= p.getId() %></td>
                            <td class="fw-semibold text-dark"><%= p.getName() %></td>
                            <td class="fw-medium">LKR <%= p.getPrice() %></td>
                            <td>
                                <span class="badge rounded-pill <%= p.getStockQuantity() > 10 ? "bg-success" : "bg-warning text-dark" %> px-3 py-2">
                                    <%= p.getStockQuantity() %> in stock
                                </span>
                            </td>
                            <td class="px-4 text-end">
                                <form action="${pageContext.request.contextPath}/admin/products/delete" method="POST" class="d-inline">
                                    <input type="hidden" name="id" value="<%= p.getId() %>">

                                    <button type="button" class="btn btn-sm btn-light border edit-btn me-1"
                                            data-bs-toggle="modal" data-bs-target="#editProductModal"
                                            data-id="<%= p.getId() %>"
                                            data-name="<%= p.getName().replace("\"", "&quot;") %>"
                                            data-desc="<%= p.getDescription() != null ? p.getDescription().replace("\"", "&quot;") : "" %>"
                                            data-price="<%= p.getPrice() %>"
                                            data-stock="<%= p.getStockQuantity() %>"
                                            data-category="<%= p.getCategory() != null ? p.getCategory().getId() : "" %>"
                                            data-color="<%= p.getColor() != null ? p.getColor().getId() : "" %>"
                                            data-storage="<%= p.getStorage() != null ? p.getStorage().getId() : "" %>"

                                            data-brand="<%= p.getBrand() != null ? p.getBrand().getId() : "" %>"

                                            data-img1="<%= p.getImage1() != null ? p.getImage1() : "" %>"
                                            data-img2="<%= p.getImage2() != null ? p.getImage2() : "" %>"
                                            data-img3="<%= p.getImage3() != null ? p.getImage3() : "" %>"
                                            data-img4="<%= p.getImage4() != null ? p.getImage4() : "" %>">
                                        <i class="bi bi-pencil text-primary"></i>
                                    </button>

                                    <button type="submit" class="btn btn-sm btn-light border" onclick="return confirm('Delete this product permanently?');">
                                        <i class="bi bi-trash text-danger"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% } } else { %>
                        <tr>
                            <td colspan="5" class="text-center py-5">
                                <div class="text-muted">
                                    <i class="bi bi-box-seam display-4 d-block mb-3 opacity-50"></i>
                                    <h5 class="fw-normal">No products found</h5>
                                </div>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/product-modals.jsp" />

<script>
    const CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/admin.js?v=<%= System.currentTimeMillis() %>"></script>
</body>
</html>