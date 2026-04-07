package kr.or.ddit.auth.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.auth.exception.AuthenticationException;
import kr.or.ddit.auth.exception.UsernameNotFoundException;
import kr.or.ddit.auth.service.AuthenticateService;
import kr.or.ddit.member.dto.MemberDTO;

public class UsernamePasswordAuthenticationFilter extends HttpFilter {
    private String loginPage;
    private String loginProcessUrl;
    private String loginFailureUrl;
    private String loginSuccessUrl;

    @Override
    public void init(FilterConfig config) throws ServletException {
        loginPage = config.getInitParameter("login-page");
        loginProcessUrl = config.getInitParameter("login-process-url");
        loginFailureUrl = config.getInitParameter("login-failure-url");
        loginSuccessUrl = config.getInitParameter("login-success-url");
        super.init(config);
    }

    private AuthenticateService authenticateService = new AuthenticateService();

    private boolean isLoginRequest(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return (requestURI.contains(loginProcessUrl) && req.getMethod().equalsIgnoreCase("post"));
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        if (isLoginRequest(req)) {
            HttpSession session = req.getSession(false);
            String lvn = null;
            String redirectAfterLogin = (session != null) ? (String) session.getAttribute("redirectAfterLogin") : null;

            if (session == null || session.isNew()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // 1. Character Encoding Filter에서 인코딩 처리 -> 특수 문자 깨짐 방지
            // -> filter에서 처리

            // 2. 파라미터 수신
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            // 3. 파라미터 검증 : 누락시 로그인 폼으로 redirect, message 전달

            // if (username == null || password == null | username.trim().isBlank() ||
            // password.trim().isBlank()) {
            // session.setAttribute("message", "Id 또는 Password 누락");
            // viewResolver.resolveViewName("redirect:/login", req, resp);
            // return;
            // }

            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                lvn = loginFailureUrl;
                session.setAttribute("message", "Id 또는 Password 누락");
                resp.sendRedirect(req.getContextPath() + lvn);
                return;
            }
            MemberDTO memberDTO;
            try {
                // 4. 인증 (AuthenticateService 의존관계)
                memberDTO = authenticateService.authenticate(username, password);
            } catch (AuthenticationException e) {
                // 인증 실패 -> 로그인 폼으로 redirect, 인증 실패 이유 message
                lvn = loginFailureUrl;
                session.setAttribute("SECURITY_LAST_EXCEPTION", e);
                resp.sendRedirect(req.getContextPath() + lvn);
                return;
            }

            // 임시로 session에 로그인 여부 추가
            session.setAttribute("authMember", memberDTO);
            lvn = loginSuccessUrl;
            // 로그인 성공 후, 원래 접근하려 했던 URI가 있다면 그쪽으로 리다이렉트
            if (StringUtils.isNotBlank(redirectAfterLogin)) {
                lvn = redirectAfterLogin;
                session.removeAttribute("redirectAfterLogin");
            }

            resp.sendRedirect(req.getContextPath() + lvn);

        } else {
            chain.doFilter(req, resp);
            if (resp.getStatus() == 401 && !resp.isCommitted()) { // 상태코드가 401이고, 응답이 결정되지 않았을때 (sendError같은거)
                resp.sendRedirect(req.getContextPath() + loginPage);
            }
        }
    }
}
