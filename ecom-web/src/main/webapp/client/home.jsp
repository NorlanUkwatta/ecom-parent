<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TechMart - Premium Electronics</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="${pageContext.request.contextPath}/css/client.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="components/navbar.jsp"/>

<section class="hero-section py-5 mb-5 bg-white border-bottom">
    <div class="container py-lg-5">
        <div class="row align-items-center">
            <div class="col-lg-6 mb-5 mb-lg-0">
                <span class="badge bg-brand bg-opacity-10 text-brand px-3 py-2 rounded-pill mb-3 fw-bold">New Arrivals 2026</span>
                <h1 class="display-3 fw-bold mb-4" style="color: #1a1a1a;">Next Generation <br><span class="text-brand">Technology.</span></h1>
                <p class="lead text-muted mb-5 pe-lg-5">Discover our curated collection of premium smartphones, accessories, and cutting-edge electronics designed for the modern lifestyle.</p>
                <div class="d-flex gap-3">
                    <a href="${pageContext.request.contextPath}/shop" class="btn btn-brand btn-lg rounded-pill px-5 shadow">Shop Now</a>
                    <a href="${pageContext.request.contextPath}/signup" class="btn btn-outline-brand btn-lg rounded-pill px-5">Join Us</a>
                </div>
            </div>
            <div class="col-lg-6 text-center">
                <img src="https://placehold.co/600x450/f8fbff/0056b3?text=TechMart+Hero+Image" class="img-fluid rounded-4 shadow-lg" alt="Latest Technology" style="transform: rotate(-2deg); transition: transform 0.3s ease;" onmouseover="this.style.transform='rotate(0deg)'" onmouseout="this.style.transform='rotate(-2deg)'">
            </div>
        </div>
    </div>
</section>

<section class="container mb-5 pb-4">
    <div class="row g-4 text-center">
        <div class="col-md-4">
            <div class="p-4 bg-white rounded-4 shadow-sm h-100 border border-light">
                <div class="d-inline-flex p-3 bg-brand bg-opacity-10 text-brand rounded-circle mb-3">
                    <i class="bi bi-truck fs-3"></i>
                </div>
                <h5 class="fw-bold">Fast Islandwide Delivery</h5>
                <p class="text-muted small mb-0">Get your products delivered securely to your doorstep within 2-3 working days across Sri Lanka.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="p-4 bg-white rounded-4 shadow-sm h-100 border border-light">
                <div class="d-inline-flex p-3 bg-brand bg-opacity-10 text-brand rounded-circle mb-3">
                    <i class="bi bi-shield-check fs-3"></i>
                </div>
                <h5 class="fw-bold">1 Year Warranty</h5>
                <p class="text-muted small mb-0">Shop with confidence. All our premium electronics come with a comprehensive local warranty.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="p-4 bg-white rounded-4 shadow-sm h-100 border border-light">
                <div class="d-inline-flex p-3 bg-brand bg-opacity-10 text-brand rounded-circle mb-3">
                    <i class="bi bi-headset fs-3"></i>
                </div>
                <h5 class="fw-bold">24/7 Customer Support</h5>
                <p class="text-muted small mb-0">Our dedicated tech support team is always ready to assist you with any inquiries or issues.</p>
            </div>
        </div>
    </div>
</section>

<jsp:include page="components/footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>