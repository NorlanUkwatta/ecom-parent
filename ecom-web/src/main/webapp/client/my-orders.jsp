<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>My Orders | TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<jsp:include page="/client/components/navbar.jsp" />

<div class="container py-5" style="max-width: 900px;">

    <div class="mb-4">
        <h2 class="fw-bold mb-1">My Orders</h2>
        <p class="text-muted">View and track your recent purchases.</p>
    </div>

    <c:choose>
        <c:when test="${empty orderList}">
            <div class="card shadow-sm border-0 rounded-4 text-center p-5">
                <div class="card-body">
                    <i class="bi bi-box-seam fs-1 text-secondary mb-3"></i>
                    <h5 class="fw-bold">No orders found</h5>
                    <p class="text-muted">Looks like you haven't made any purchases yet.</p>
                    <a href="${pageContext.request.contextPath}/shop" class="btn btn-primary rounded-pill px-4 mt-2">Start Shopping</a>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <div class="d-flex flex-column gap-3">
                <c:forEach var="order" items="${orderList}">

                    <div class="card shadow-sm border-0 rounded-4 overflow-hidden">

                        <div class="card-header bg-white border-bottom py-3 d-flex justify-content-between align-items-center">
                            <div>
                                <span class="text-muted small">Order Number</span>
                                <h6 class="mb-0 fw-bold">#${order.id}</h6>
                            </div>

                            <c:choose>
                                <c:when test="${order.status == 'PAID'}">
                                    <span class="badge bg-success bg-opacity-10 text-success border border-success rounded-pill px-3 py-2">Paid & Processing</span>
                                </c:when>
                                <c:when test="${order.status == 'PENDING'}">
                                    <span class="badge bg-warning bg-opacity-10 text-warning border border-warning rounded-pill px-3 py-2">Pending Payment</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary bg-opacity-10 text-secondary border border-secondary rounded-pill px-3 py-2">${order.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="card-body p-4 d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="fw-bold text-primary mb-1">LKR <fmt:formatNumber value="${order.totalAmount}" type="number" maxFractionDigits="2"/></h4>
                                <span class="text-muted small">
                                    <i class="bi bi-calendar3 me-1"></i> Placed successfully
                                </span>
                            </div>

                            <button class="btn btn-outline-primary btn-sm rounded-pill px-3">
                                View Details
                            </button>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>