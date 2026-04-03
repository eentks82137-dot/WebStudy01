package kr.or.ddit.auth.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends HttpFilter {
    private Map<String, List<String>> securedResources;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);

        securedResources = new LinkedHashMap<>();
        securedResources.put("/hw04/convert", List.of("ROLE_USER", "ROLE_ADMIN"));
        securedResources.put("/hw05/exchange", List.of("ROLE_ADMIN"));

    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        boolean isSecuredResource = securedResources.containsKey(requestURI);
        boolean isAuthenticated = false;
        boolean isGranted = false;

        requestURI = requestURI.substring(req.getContextPath().length());
        if (isSecuredResource) {
            Principal principal = req.getUserPrincipal();
            isAuthenticated = principal != null;
            if (isAuthenticated) {
                List<String> grantedRoles = securedResources.get(requestURI);
                isGranted = grantedRoles.stream().anyMatch(req::isUserInRole);
            }
        }
        boolean pass = !isSecuredResource || (isAuthenticated && isGranted);

        if (pass) {
            chain.doFilter(req, res);
        } else if (!isAuthenticated) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            res.sendError(403, "권한 없음");
        }
    }
}
