<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Notification Center | TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<jsp:include page="/client/components/navbar.jsp" />

<div class="container py-5" style="max-width: 800px;">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold mb-0">Notifications</h2>

        <form method="post" action="${pageContext.request.contextPath}/notifications">
            <input type="hidden" name="action" value="markAllRead">
            <button type="submit" class="btn btn-outline-primary btn-sm rounded-pill">
                <i class="bi bi-check-all"></i> Mark all as read
            </button>
        </form>
    </div>

    <div class="card shadow-sm border-0 rounded-4">
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty notificationList}">
                    <div class="text-center p-5 text-muted">
                        <i class="bi bi-bell-slash fs-1 text-secondary mb-3"></i>
                        <h5>You have no notifications yet.</h5>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="list-group list-group-flush rounded-4">

                        <c:forEach var="notif" items="${notificationList}">

                            <div class="list-group-item p-4 border-bottom ${notif.read ? 'bg-light text-muted' : 'bg-white'}">
                                <div class="d-flex w-100 justify-content-between align-items-start">
                                    <div>
                                        <h6 class="mb-1 ${notif.read ? '' : 'fw-bold'}">${notif.title}</h6>
                                        <p class="mb-1 small">${notif.message}</p>
                                        <small class="text-secondary">
                                            <fmt:formatDate value="${notif.createdAt}" pattern="MMM dd, yyyy - hh:mm a" />
                                        </small>
                                    </div>

                                    <c:if test="${!notif.read}">
                                        <form method="post" action="${pageContext.request.contextPath}/notifications">
                                            <input type="hidden" name="action" value="markSingle">
                                            <input type="hidden" name="id" value="${notif.id}">
                                            <button type="submit" class="btn btn-sm btn-primary rounded-circle" title="Mark as Read">
                                                <i class="bi bi-check2"></i>
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>

                        </c:forEach>

                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>