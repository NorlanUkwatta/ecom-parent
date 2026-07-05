<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-dark bg-dark shadow-sm sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="#">TechMart Admin</a>
        <div class="d-flex">
            <span class="navbar-text me-3 text-white">
                Welcome, ${sessionScope.loggedUser.username}
            </span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>