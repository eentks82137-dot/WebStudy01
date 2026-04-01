package kr.or.ddit.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewResolverComposite implements ViewResolver {
    private final List<ViewResolver> viewResolvers;
    {
        viewResolvers = new ArrayList<>();
        viewResolvers.add(new InternalResourceViewResolver());
    }

    @Override
    public void resolveViewName(String logicalViewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (logicalViewName.startsWith("redirect:")) {
            logicalViewName = logicalViewName.substring("redirect:".length());
            resp.sendRedirect(req.getContextPath() + logicalViewName);
            return;
        }

        for (ViewResolver viewResolver : viewResolvers) {
            try {
                viewResolver.resolveViewName(logicalViewName, req, resp);
                break;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }
        }
    }

}
