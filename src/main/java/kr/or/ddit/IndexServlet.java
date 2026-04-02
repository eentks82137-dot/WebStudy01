package kr.or.ddit;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOInMemoryImpl;
import kr.or.ddit.member.dto.MemberDTO;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/index.do")
public class IndexServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String loginUser = (String) session.getAttribute("loginUser");
        if (loginUser != null) {
            MemberDTO memberDTO = new MemberDAOInMemoryImpl().selectMember(loginUser);
            memberDTO.setMemPass("");
            req.setAttribute("memberInfo", memberDTO);
        }
        String lvn = "index";
        viewResolver.resolveViewName(lvn, req, resp);

    }
}
