<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Orders | TechMart Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<div class="container py-5">
    <h2 class="fw-bold mb-4">Manage Orders</h2>

    <div class="card shadow-sm border-0">
        <div class="card-body p-0">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                <tr>
                    <th class="px-4 py-3">Order ID</th>
                    <th>Customer</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th class="text-end px-4">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td class="px-4 fw-bold">#${order.id}</td>
                        <td>${order.user.username}</td>
                        <td>LKR <fmt:formatNumber value="${order.totalAmount}" type="number"/></td>

                        <td>
                            <span class="badge bg-primary rounded-pill px-3 py-2">${order.status}</span>
                        </td>

                        <td class="text-end px-4">
                            <div class="d-flex justify-content-end gap-2">

                                <button type="button" class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#orderModal${order.id}">
                                    <i class="bi bi-card-text"></i> Details
                                </button>

                                <form method="post" action="${pageContext.request.contextPath}/admin/orders" class="d-flex m-0 gap-2">
                                    <input type="hidden" name="action" value="updateStatus">
                                    <input type="hidden" name="orderId" value="${order.id}">

                                    <select name="status" class="form-select form-select-sm" style="width: auto;">
                                        <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                        <option value="PAID" ${order.status == 'PAID' ? 'selected' : ''}>Paid</option>
                                        <option value="PROCESSING" ${order.status == 'PROCESSING' ? 'selected' : ''}>Processing</option>
                                        <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>Shipped</option>
                                        <option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                                        <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                                    </select>

                                    <button type="submit" class="btn btn-sm btn-success">Update</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<c:forEach var="order" items="${orderList}">
    <div class="modal fade" id="orderModal${order.id}" tabindex="-1" aria-labelledby="modalLabel${order.id}" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title fw-bold" id="modalLabel${order.id}">Order #${order.id} Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <h6 class="text-uppercase text-muted fw-bold mb-2" style="font-size: 0.8rem;">Customer Contact</h6>
                    <p class="mb-1"><strong>Name:</strong> ${order.shippingAddress.firstName} ${order.shippingAddress.lastName}</p>
                    <p class="mb-1"><strong>Email:</strong> ${order.shippingAddress.email}</p>
                    <p class="mb-4"><strong>Mobile:</strong> ${order.shippingAddress.mobile}</p>

                    <h6 class="text-uppercase text-muted fw-bold mb-2" style="font-size: 0.8rem;">Shipping Address</h6>
                    <p class="mb-1">${order.shippingAddress.streetAddress}</p>
                    <p class="mb-1">${order.shippingAddress.city}, ${order.shippingAddress.postalCode}</p>
                    <p class="mb-0">${order.shippingAddress.country}</p>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary rounded-pill" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>