<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Log In - TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8fbff; font-family: 'Segoe UI', sans-serif; }
        .auth-card { border: none; border-radius: 12px; box-shadow: 0 10px 40px rgba(0, 86, 179, 0.08); }
        .btn-primary { background-color: #0056b3; border-color: #0056b3; font-weight: 600; padding: 12px; }
        .btn-primary:hover { background-color: #004494; border-color: #004494; }
        .form-control:focus { border-color: #0056b3; box-shadow: 0 0 0 0.25rem rgba(0, 86, 179, 0.15); }
        .brand-text { color: #0056b3; font-weight: 800; letter-spacing: -1px; }
    </style>
</head>
<body class="d-flex align-items-center py-5 min-vh-100">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5 col-lg-4">
            <div class="text-center mb-4">
                <h1 class="brand-text display-5">TechMart</h1>
                <p class="text-muted">Welcome back to your account</p>
            </div>

            <div class="card auth-card bg-white p-4">
                <div class="card-body">
                    <% if (request.getSession().getAttribute("successMessage") != null) { %>
                    <div class="alert alert-success border-0 rounded-3 small text-center fw-semibold">
                        <%= request.getSession().getAttribute("successMessage") %>
                    </div>
                    <% request.getSession().removeAttribute("successMessage"); %>
                    <% } %>
                    <% if (request.getSession().getAttribute("errorMessage") != null) { %>
                    <div class="alert alert-danger border-0 rounded-3 small text-center fw-semibold">
                        <%= request.getSession().getAttribute("errorMessage") %>
                    </div>
                    <% request.getSession().removeAttribute("errorMessage"); %>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/login" method="POST">
                        <div class="mb-3">
                            <label class="form-label text-muted small fw-bold text-uppercase">Email, Mobile, or Username</label>
                            <input type="text" class="form-control form-control-lg fs-6" name="identifier" required autofocus>
                        </div>
                        <div class="mb-4">
                            <label class="form-label text-muted small fw-bold text-uppercase">Password</label>
                            <input type="password" class="form-control form-control-lg fs-6" name="password" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 rounded-3">Sign In</button>
                    </form>

                    <div class="text-center mt-4 pt-3 border-top">
                        <span class="text-muted small">New to TechMart?</span>
                        <a href="${pageContext.request.contextPath}/signup" class="text-decoration-none fw-bold text-primary">Create an account</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>