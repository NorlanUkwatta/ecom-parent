<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.techmart.entity.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Customers - TechMart Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body>

<jsp:include page="components/navbar.jsp" />

<div class="container-fluid">
  <div class="row">

    <jsp:include page="components/sidebar.jsp" />

    <div class="col-md-10 p-4">

      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Customer Management</h2>
      </div>

      <% if (request.getAttribute("successMessage") != null) { %>
      <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle-fill me-2"></i> <%= request.getAttribute("successMessage") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
      <% } %>

      <div class="card shadow-sm border-0">
        <div class="card-body p-0">
          <table class="table table-hover mb-0 align-middle">
            <thead class="table-light">
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Mobile</th>
              <th>Status</th>
              <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
              List<User> customers = (List<User>) request.getAttribute("customers");
              if (customers != null && !customers.isEmpty()) {
                for (User c : customers) {
            %>
            <tr>
              <td><%= c.getId() %></td>
              <td class="fw-semibold"><%= c.getUsername() %></td>
              <td><%= c.getEmail() %></td>
              <td><%= c.getMobile() != null ? c.getMobile() : "N/A" %></td>
              <td>
                <% if(c.isActive()) { %>
                <span class="badge bg-success">Active</span>
                <% } else { %>
                <span class="badge bg-danger">Inactive</span>
                <% } %>
              </td>
              <td class="text-end">
                <form action="${pageContext.request.contextPath}/admin/customers" method="POST" class="d-inline">
                  <input type="hidden" name="userId" value="<%= c.getId() %>">

                  <% if(c.isActive()) { %>
                  <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure you want to deactivate this customer?');">
                    <i class="bi bi-person-x"></i> Deactivate
                  </button>
                  <% } else { %>
                  <button type="submit" class="btn btn-sm btn-outline-success" onclick="return confirm('Are you sure you want to reactivate this customer?');">
                    <i class="bi bi-person-check"></i> Reactivate
                  </button>
                  <% } %>

                </form>
              </td>
            </tr>
            <% } } else { %>
            <tr><td colspan="6" class="text-center text-muted py-4">No customers found.</td></tr>
            <% } %>
            </tbody>
          </table>
        </div>
      </div>

    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>