package kr.or.ddit.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BlindFilter extends HttpFilter {

    private List<String> whitelist;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init();
        whitelist = Arrays.asList(config.getInitParameter("whitelist").split(","));
        // WSL ip addr : 172.19.227.207
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String ip = req.getRemoteAddr();
        System.out.println(ip);

        if (!StringUtils.isBlank(ip) && whitelist.contains(ip)) {
            chain.doFilter(req, res);
        } else {
            res.sendError(400);
        }

    }
}
