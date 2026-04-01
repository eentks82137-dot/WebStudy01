package kr.or.ddit.mvc;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InternalResourceViewResolver implements ViewResolver {

    /**
     * 
     * @param logicalViewName
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void resolveViewName(String logicalViewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String prefix = "/WEB-INF/views/";
        String suffix = ".jsp";
        String view = prefix + logicalViewName + suffix;
        req.getRequestDispatcher(view).forward(req, resp);
    }
}
