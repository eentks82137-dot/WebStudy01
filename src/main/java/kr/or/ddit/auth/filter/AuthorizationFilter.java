package kr.or.ddit.auth.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.auth.exception.SecuredResourceNotFoundException;

public class AuthorizationFilter extends HttpFilter {
    private Map<String, List<String>> securedResources;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        String location = config.getInitParameter("location");
        securedResources = generateSecuredResources(location);

    }

    private Map<String, List<String>> generateSecuredResources(String location) {
        String baseName = location;
        if (location == null) {
            throw new SecuredResourceNotFoundException();
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
        Enumeration<String> keys = resourceBundle.getKeys();
        Map<String, List<String>> securedResources = new LinkedHashMap<>();
        while (keys.hasMoreElements()) {
            String code = (String) keys.nextElement();
            String message = resourceBundle.getString(code);

            List<String> roles = Arrays.asList(message.toString().split(","));
            System.out.println("URL: %s | ROLES : %s".formatted(code, roles.toString()));
            securedResources.put(code, roles);
        }
        return securedResources;
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
            // 로그인이 필요할 경우, 접근하고자 했던 URI를 쿼리스트링으로 전달하여 로그인 후 리다이렉트할 수 있도록 처리
            req.getSession().setAttribute("redirectAfterLogin", requestURI);
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            res.sendError(403, "권한 없음");
        }
    }
}
