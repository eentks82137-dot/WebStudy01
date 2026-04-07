package kr.or.ddit.common;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/login")
public class LoginUiServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // HttpSession session = req.getSession();
        // String message = (String) session.getAttribute("message");
        // if (message != null) {
        // req.setAttribute("message", message);
        // session.removeAttribute("message");
        // }

        String lvn = "login/loginForm";
        viewResolver.resolveViewName(lvn, req, resp);
    }

}
