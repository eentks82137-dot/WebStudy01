package kr.or.ddit.auth.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AccessLoggingFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(AccessLoggingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String requestId = UUID.randomUUID().toString();
        long startNanos = System.nanoTime();
        String requestURI = req.getRequestURI();
        Principal principal = req.getUserPrincipal();
        String userId = (principal != null) ? principal.getName() : "anonymous";
        String clientIP = req.getRemoteAddr();
        String method = req.getMethod();

        MDC.put("requestId", requestId);
        try {
            chain.doFilter(req, res);
        } finally {
            long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000L;
            int status = res.getStatus();
            if (status >= 500) {
                logger.error(
                        "AccessLoggingFilter - requestId: {}, userId: {}, requestURI: {}, clientIP: {}, method: {}, status: {}, elapsedMs: {}",
                        requestId, userId, requestURI, clientIP, method, status, elapsedMs);
            } else if (status >= 400) {
                logger.warn(
                        "AccessLoggingFilter - requestId: {}, userId: {}, requestURI: {}, clientIP: {}, method: {}, status: {}, elapsedMs: {}",
                        requestId, userId, requestURI, clientIP, method, status, elapsedMs);
            } else {
                logger.info(
                        "AccessLoggingFilter - requestId: {}, userId: {}, requestURI: {}, clientIP: {}, method: {}, status: {}, elapsedMs: {}",
                        requestId, userId, requestURI, clientIP, method, status, elapsedMs);
            }
            MDC.remove("requestId");
        }
    }
}
