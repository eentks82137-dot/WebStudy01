package kr.or.ddit.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/logout")
public class TempLogoutServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();

    // 임시, 삭제예정
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String loginUser = (String) session.getAttribute("loginUser");
        if (loginUser != null) {
            session.removeAttribute("loginUser");
            viewResolver.resolveViewName("redirect:/index.do", req, resp);
            return;
        } else {
            viewResolver.resolveViewName("redirect:/index.do", req, resp);
            return;
        }

    }
}
