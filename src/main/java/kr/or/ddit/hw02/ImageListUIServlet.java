package kr.or.ddit.hw02;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/hw02/imageList")
public class ImageListUIServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> imageList = GetImageList.getImageList();

        req.setAttribute("imageList", imageList);
        viewResolver.resolveViewName("/hw02/image-list", req, resp);
        // req.getRequestDispatcher("/WEB-INF/views/hw02/image-list.jsp").forward(req,
        // resp);
    }
}
