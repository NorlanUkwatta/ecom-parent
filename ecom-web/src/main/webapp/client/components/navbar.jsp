<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary py-3 shadow-sm sticky-top">
    <div class="container">
        <a class="navbar-brand fs-3" href="${pageContext.request.contextPath}/home">TechMart.</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link fw-medium"
                                        href="${pageContext.request.contextPath}/home">Home</a></li>
                <li class="nav-item"><a class="nav-link fw-medium" href="${pageContext.request.contextPath}/shop">Shop
                    All</a></li>
                <li class="nav-item"><a class="nav-link fw-medium" href="#">New Arrivals</a></li>
            </ul>

            <div class="d-flex align-items-center gap-3">
                <%
                    int cartCount = 0;
                    Map<Long, Integer> guestCart = (Map<Long, Integer>) request.getSession().getAttribute("guestCart");
                    if (guestCart != null) {
                        for (Integer qty : guestCart.values()) {
                            cartCount += qty;
                        }
                    }
                    if (request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("cartCount") != null) {
                        cartCount = (Integer) request.getSession().getAttribute("cartCount");
                    }
                %>

                <a href="${pageContext.request.contextPath}/cart" class="position-relative text-white text-decoration-none">
                    <i class="bi bi-cart3 fs-4"></i>
                    <% if (cartCount > 0) { %>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger shadow-sm" style="font-size: 0.65rem;">
                        <%= cartCount %>
                    </span>
                    <% } %>
                </a>

                <% if (request.getSession().getAttribute("loggedUser") != null) { %>

                <div class="dropdown">
                    <a class="position-relative text-white text-decoration-none" href="#" id="notificationDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-bell fs-4"></i>
                        <span id="notifBadge" class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger shadow-sm" style="display: none; font-size: 0.65rem;">
                            0
                        </span>
                    </a>

                    <ul class="dropdown-menu dropdown-menu-end shadow-sm border-0 mt-3" aria-labelledby="notificationDropdown" style="width: 320px; max-height: 400px; overflow-y: auto;">
                        <li><h6 class="dropdown-header fw-bold text-primary">Recent Notifications</h6></li>
                        <div id="notifContainer">
                            <li><span class="dropdown-item text-muted small text-center py-2">Loading...</span></li>
                        </div>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item text-center text-primary fw-bold py-2" href="${pageContext.request.contextPath}/notifications">
                                View All Notifications
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="dropdown">
                    <button class="btn btn-light rounded-pill px-4 fw-bold dropdown-toggle shadow-sm" type="button" data-bs-toggle="dropdown">
                        <i class="bi bi-person-circle me-1"></i> Account
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end shadow border-0 mt-2">
                        <li><a class="dropdown-item py-2" href="${pageContext.request.contextPath}/my-orders"><i class="bi bi-bag-check me-2 text-brand"></i>My Orders</a></li>
                        <li><a class="dropdown-item py-2" href="#"><i class="bi bi-gear me-2 text-brand"></i>Settings</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item py-2 text-danger" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Sign Out</a></li>
                    </ul>
                </div>

                <% } else { %>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-light rounded-pill px-4 fw-bold shadow-sm">Sign In</a>
                <% } %>
            </div>

        </div>
    </div>
</nav>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const contextPath = '${pageContext.request.contextPath}';
        const notifContainer = document.getElementById('notifContainer');
        const notifBadge = document.getElementById('notifBadge');

        function loadNotifications() {
            fetch(contextPath + '/fetch-notifications')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Server returned ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    notifContainer.innerHTML = '';

                    if (data.length === 0) {
                        notifContainer.innerHTML = '<li><span class="dropdown-item text-muted small text-center py-3">No new notifications</span></li>';
                        notifBadge.style.display = 'none';
                        return;
                    }

                    notifBadge.innerText = data.length;
                    notifBadge.style.display = 'inline-block';

                    data.forEach(notif => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                        <a class="dropdown-item py-2 border-bottom notif-item" href="#" data-id="\${notif.id}">
                            <div class="d-flex justify-content-between align-items-center">
                                <strong class="text-dark small">\${notif.title}</strong>
                                <span class="badge bg-primary rounded-pill p-1 ms-2" title="Mark as read"><i class="bi bi-check2"></i></span>
                            </div>
                            <div class="text-muted" style="font-size: 0.8rem; white-space: normal;">\${notif.message}</div>
                        </a>
                    `;
                        notifContainer.appendChild(li);
                    });

                    attachClickEvents();
                })
                .catch(error => console.error('Error fetching notifications:', error));
        }

        function attachClickEvents() {
            document.querySelectorAll('.notif-item').forEach(item => {
                item.addEventListener('click', function(e) {
                    e.preventDefault();
                    e.stopPropagation(); // Keep dropdown open if clicked

                    const notifId = this.getAttribute('data-id');

                    fetch(contextPath + '/fetch-notifications', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                        body: 'id=' + notifId
                    }).then(() => {
                        this.remove();
                        loadNotifications();
                    });
                });
            });
        }

        loadNotifications();

        setInterval(loadNotifications, 30000);
    });
</script>