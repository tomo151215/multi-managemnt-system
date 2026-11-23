package jp.m.multi.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class SecurityHeadersFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse http = (HttpServletResponse) response;

        http.setHeader("X-Content-Type-Options", "nosniff");
        http.setHeader("X-Frame-Options", "DENY");
        http.setHeader("Referrer-Policy", "same-origin");
        StringBuilder policy = new StringBuilder();
        policy.append("default-src 'self'; ")
                .append("script-src 'self'; ")
                .append("style-src 'self'; ")
                .append("img-src 'self' data:; ")
                .append("object-src 'none'; ")
                .append("base-uri 'none'; ")
                .append("frame-ancestors 'none'; ")
                .append("frame-ancestors 'none'; ")
                .append("form-action 'self'");
                
        http.setHeader("Content-Security-Policy", policy.toString());
        http.setHeader("Cache-Control", "no-store");

        chain.doFilter(request, response);
    }
}