package com.techmart.web.filter;

import com.techmart.core.enums.Role;
import com.techmart.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpsRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpsRequest.getSession(false);

        boolean isLoggedIn = (httpSession != null && httpSession.getAttribute("loggedUser") != null);
        if (isLoggedIn) {
            User user = (User) httpSession.getAttribute("loggedUser");
            if (user.getRole() == Role.ADMIN) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(httpsRequest.getContextPath() + "/shop");
            }
        } else {
            httpResponse.sendRedirect(httpsRequest.getContextPath() + "/login");
        }
    }
}
