<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Secure Checkout - TechMart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<jsp:include page="components/navbar.jsp"/>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card border-0 shadow-sm rounded-4">
                <div class="card-body p-5">
                    <h3 class="fw-bold mb-4">Shipping Information</h3>

                    <form action="${pageContext.request.contextPath}/checkout/process" method="POST">

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label fw-medium">First Name</label>
                                <input type="text" name="firstName" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-medium">Last Name</label>
                                <input type="text" name="lastName" class="form-control" required>
                            </div>

                            <div class="col-md-6">
                                <label class="form-label fw-medium">Email Address</label>
                                <input type="email" name="email" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-medium">Mobile Number</label>
                                <input type="tel" name="mobile" class="form-control" required>
                            </div>

                            <div class="col-12">
                                <label class="form-label fw-medium">Street Address</label>
                                <input type="text" name="streetAddress" class="form-control" required>
                            </div>

                            <div class="col-md-5">
                                <label class="form-label fw-medium">Country</label>
                                <select name="country" class="form-select" required>
                                    <option value="" disabled selected>Choose...</option>
                                    <option value="Sri Lanka">Sri Lanka</option>
                                    <option value="United Kingdom">United Kingdom</option>
                                    <option value="Australia">Australia</option>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label fw-medium">City</label>
                                <input type="text" name="city" class="form-control" required>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label fw-medium">Postal Code</label>
                                <input type="text" name="postalCode" class="form-control" required>
                            </div>
                        </div>

                        <hr class="my-5">

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/cart" class="btn btn-light px-4 me-md-2">Back to Cart</a>
                            <button type="submit" class="btn btn-primary px-5 fw-bold">Continue to Payment</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>