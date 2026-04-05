package kr.or.ddit.auth.filter;

import java.io.IOException;
import java.security.Principal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessLoggingFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(AccessLoggingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        Principal principal = req.getUserPrincipal();
        String userId = (principal != null) ? principal.getName() : "anonymous";
        logger.info("AccessLoggingFilter - userId: {}, requestURI: {}", userId, requestURI);

        chain.doFilter(req, res);
    }
}
