package kr.or.ddit.auth.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.auth.wrapper.PrincipalRequestWrapper;

public class AuthenticationHolderFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        PrincipalRequestWrapper wrapper = new PrincipalRequestWrapper(req);
        chain.doFilter(wrapper, res);
    }
}
