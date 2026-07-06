package com.techmart.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(filterName = "PerformanceFilter", urlPatterns = {"/*"})
public class PerformanceFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger("techmart.metrics.http");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;

        try {

            chain.doFilter(request, response);

        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            String uri = req.getRequestURI();

            if (!uri.endsWith(".css") && !uri.endsWith(".js") && !uri.endsWith(".png") && !uri.endsWith(".jpg")) {

                logger.warn("[HTTP METRIC] {} {} completed in {} ms",
                        req.getMethod(), uri, executionTime);

                if (executionTime > 1000) {
                    logger.warn("[SLOW PAGE LOAD] {} took {} ms to render!", uri, executionTime);
                }
            }
        }
    }
}
