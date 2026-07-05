<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>System Notifications | TechMart Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">

<div class="container py-5">
  <h2 class="fw-bold mb-4">System Alerts & Notifications</h2>

  <div class="card shadow-sm border-0">
    <div class="card-body p-0">
      <c:choose>
        <c:when test="${empty notificationList}">
          <div class="text-center p-5 text-muted">
            <i class="bi bi-bell-slash fs-1 text-secondary mb-3"></i>
            <h5>No system notifications found.</h5>
          </div>
        </c:when>
        <c:otherwise>
          <div class="list-group list-group-flush">
            <c:forEach var="notif" items="${notificationList}">
              <div class="list-group-item p-4 border-bottom">
                <div class="d-flex w-100 justify-content-between align-items-center">
                  <div>
                    <h6 class="mb-1 fw-bold text-primary">
                      <i class="bi bi-info-circle me-2"></i>${notif.title}
                    </h6>
                    <p class="mb-1 text-dark">${notif.message}</p>
                    <small class="text-muted">
                      <fmt:formatDate value="${notif.createdAt}" pattern="MMM dd, yyyy - hh:mm a" />
                    </small>
                  </div>
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