package kr.or.ddit.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.admin.service.AdminMemberService;
import kr.or.ddit.member.dto.MemberDTO;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/admin/allMembers")
public class GetAllMembersServlet extends HttpServlet {
    private AdminMemberService adminMemberService = new AdminMemberService();
    private ViewResolver resolver = new ViewResolverComposite();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<MemberDTO> memberDTOs = adminMemberService.getAllMembers();
        req.setAttribute("memberList", memberDTOs);
        String lvn = "/admin/member-list";
        resolver.resolveViewName(lvn, req, resp);

    }
}
