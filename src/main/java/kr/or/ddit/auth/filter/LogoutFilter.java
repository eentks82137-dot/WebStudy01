package kr.or.ddit.auth.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.member.dto.MemberDTO;

public class LogoutFilter extends HttpFilter {

    private String logoutSuccessUrl;
    private String logoutUrl;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        logoutUrl = config.getInitParameter("logout-url");
        logoutSuccessUrl = config.getInitParameter("logout-success-url");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String reqUri = req.getRequestURI();

        if (reqUri.contains(logoutUrl)) {
            HttpSession session = req.getSession();
            MemberDTO authMember = (MemberDTO) session.getAttribute("authMember");
            if (authMember != null) {
                session.invalidate();
                res.sendRedirect(req.getContextPath() + logoutSuccessUrl);
                return;
            } else {
                res.sendRedirect(req.getContextPath() + "/index.do");
                return;
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}
