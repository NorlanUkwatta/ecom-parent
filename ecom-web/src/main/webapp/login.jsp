<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TechMart - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center vh-100">

    <div class="card shadow-lg border-0" style="width: 100%; max-width: 400px; border-radius: 1rem;">
        <div class="card-body p-5">

            <div class="text-center mb-4">
                <h2 class="fw-bold text-primary">TechMart</h2>
                <p class="text-muted">Sign in to your account</p>
            </div>

            <%-- Dynamic Error Message Alert --%>
            <% if(request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger text-center" role="alert">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>

            <form action="login" method="POST">

                <div class="mb-3">
                    <label for="username" class="form-label fw-semibold">Username</label>
                    <input type="text" class="form-control form-control-lg" id="username" name="username" placeholder="Enter your username" required autofocus>
                </div>

                <div class="mb-4">
                    <label for="password" class="form-label fw-semibold">Password</label>
                    <input type="password" class="form-control form-control-lg" id="password" name="password" placeholder="Enter your password" required>
                </div>

                <button type="submit" class="btn btn-primary btn-lg w-100 fw-bold">Login</button>

            </form>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>