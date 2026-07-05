<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-2 bg-light border-end min-vh-100 p-3">
    <h6 class="text-uppercase text-muted fw-bold mb-3" style="font-size: 0.8rem;">Menu</h6>
    <div class="nav flex-column nav-pills">
        <a href="${pageContext.request.contextPath}/admin/inventory" class="nav-link text-dark mb-2">
            <i class="bi bi-box-seam me-2"></i> Inventory
        </a>
        <a href="${pageContext.request.contextPath}/admin/orders" class="nav-link text-dark mb-2">
            <i class="bi bi-cart-check me-2"></i> Orders
        </a>
        <a href="${pageContext.request.contextPath}/admin/customers" class="nav-link text-dark mb-2">
            <i class="bi bi-people me-2"></i> Customers
        </a>

        <a href="${pageContext.request.contextPath}/admin/notifications" class="nav-link text-dark mb-2">
            <i class="bi bi-bell me-2"></i> Notifications
        </a>
    </div>
</div>