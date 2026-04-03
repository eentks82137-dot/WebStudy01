package kr.or.ddit.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.filter.wrapper.LocaleParameterTrickRequestWrapper;

public class WorldTimeTrickFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        LocaleParameterTrickRequestWrapper wrapper = new LocaleParameterTrickRequestWrapper(req);
        chain.doFilter(wrapper, res);
    }
}
