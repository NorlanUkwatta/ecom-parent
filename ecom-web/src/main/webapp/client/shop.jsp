<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.techmart.entity.Product" %>
<%@ page import="com.techmart.entity.Category" %>
<%@ page import="com.techmart.entity.Color" %>
<%@ page import="com.techmart.entity.Brand" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop - TechMart</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <link href="${pageContext.request.contextPath}/css/client.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="components/navbar.jsp"/>

<div class="container py-5">
    <form action="${pageContext.request.contextPath}/shop" method="GET" id="filterForm">
        <div class="row g-4">

            <aside class="col-lg-3 d-none d-lg-block">
                <div class="filter-section position-sticky" style="top: 100px;">

                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h5 class="fw-bold mb-0">Filters</h5>
                        <a href="${pageContext.request.contextPath}/shop"
                           class="text-decoration-none small text-danger">Clear All</a>
                    </div>

                    <h6 class="text-uppercase text-muted small fw-bold mb-3">Categories</h6>
                    <div class="mb-4">
                        <div class="form-check mb-2">
                            <input class="form-check-input" type="radio" name="category" value="" id="catAll"
                                   onchange="this.form.submit()" <%= request.getAttribute("selCat") == null ? "checked" : "" %>>
                            <label class="form-check-label text-dark" for="catAll">All Categories</label>
                        </div>
                        <%
                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                            String selCat = (String) request.getAttribute("selCat");
                            if (categories != null) {
                                for (Category cat : categories) {
                        %>
                        <div class="form-check mb-2">
                            <input class="form-check-input" type="radio" name="category" value="<%= cat.getId() %>"
                                   id="cat<%= cat.getId() %>"
                                   onchange="this.form.submit()" <%= (selCat != null && selCat.equals(cat.getId().toString())) ? "checked" : "" %>>
                            <label class="form-check-label text-dark" for="cat<%= cat.getId() %>"><%= cat.getName() %>
                            </label>
                        </div>
                        <% }
                        } %>
                    </div>

                    <h6 class="text-uppercase text-muted small fw-bold mb-3">Brands</h6>
                    <div class="mb-4">
                        <div class="form-check mb-2">
                            <input class="form-check-input" type="radio" name="brand" value="" id="brandAll"
                                   onchange="this.form.submit()" <%= request.getAttribute("selBrand") == null ? "checked" : "" %>>
                            <label class="form-check-label text-dark" for="brandAll">All Brands</label>
                        </div>
                        <%
                            List<Brand> brands = (List<Brand>) request.getAttribute("brands");
                            String selBrand = (String) request.getAttribute("selBrand");
                            if (brands != null) {
                                for (Brand b : brands) {
                        %>
                        <div class="form-check mb-2">
                            <input class="form-check-input" type="radio" name="brand" value="<%= b.getId() %>"
                                   id="brand<%= b.getId() %>"
                                   onchange="this.form.submit()" <%= (selBrand != null && selBrand.equals(b.getId().toString())) ? "checked" : "" %>>
                            <label class="form-check-label text-dark" for="brand<%= b.getId() %>"><%= b.getName() %>
                            </label>
                        </div>
                        <% }
                        } %>
                    </div>

                </div>
            </aside>

            <main class="col-lg-9">

                <div class="d-flex justify-content-between align-items-center mb-4 pb-3 border-bottom">
                    <h2 class="fw-bold mb-0">Explore Catalog</h2>

                    <% String selSort = (String) request.getAttribute("selSort"); %>
                    <select class="form-select w-auto border-0 shadow-sm rounded-3 fw-medium" name="sort"
                            onchange="this.form.submit()">
                        <option value="newest" <%= (selSort == null || selSort.equals("newest")) ? "selected" : "" %>>
                            Sort by: Newest
                        </option>
                        <option value="price_asc" <%= ("price_asc".equals(selSort)) ? "selected" : "" %>>Price: Low to
                            High
                        </option>
                        <option value="price_desc" <%= ("price_desc".equals(selSort)) ? "selected" : "" %>>Price: High
                            to Low
                        </option>
                    </select>
                </div>

                <div class="row g-4">
                    <%
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        if (products != null && !products.isEmpty()) {
                            for (Product p : products) {
                    %>

                    <div class="col-6 col-md-6 col-lg-4">
                        <div class="card product-card shadow-sm h-100">

                            <a href="${pageContext.request.contextPath}/shop/product?id=<%= p.getId() %>"
                               class="product-img-wrap p-3">
                                <img src="${pageContext.request.contextPath}/images/<%= p.getImage1() != null ? p.getImage1() : "placeholder.jpg" %>"
                                     class="img-fluid"
                                     alt="<%= p.getName() %>">
                            </a>

                            <div class="card-body d-flex flex-column p-3">
                                <div class="mb-2">
                                    <span class="badge category-pill text-uppercase px-2 py-1 mb-2" style="font-size: 0.65rem;">
                                        <%= p.getCategory() != null ? p.getCategory().getName() : "General" %>
                                    </span>
                                    <h6 class="card-title fw-bold text-dark text-truncate mb-1" title="<%= p.getName() %>">
                                        <%= p.getName() %>
                                    </h6>
                                </div>

                                <div class="mt-auto d-flex justify-content-between align-items-center pt-2 border-top mt-2">
                                    <span class="fs-5 fw-bold text-brand">LKR <%= String.format("%.2f", p.getPrice()) %></span>

                                    <a href="${pageContext.request.contextPath}/shop/product?id=<%= p.getId() %>"
                                       class="btn btn-sm btn-outline-brand rounded-pill px-3 py-1">
                                        <i class="bi bi-bag-plus"></i>
                                    </a>
                                </div>
                            </div>

                        </div>
                    </div>

                    <%
                        }
                    } else {
                    %>
                    <div class="col-12 text-center py-5">
                        <i class="bi bi-search display-1 text-muted opacity-25"></i>
                        <h4 class="mt-3 text-muted">No products match your filters.</h4>
                        <a href="${pageContext.request.contextPath}/shop" class="btn btn-outline-brand mt-2">Clear
                            Filters</a>
                    </div>
                    <% } %>
                </div>

            </main>
        </div>
    </form>
</div>
<jsp:include page="components/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>