package kr.or.ddit.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.admin.service.AdminLogService;
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

        String accept = req.getHeader("Accept");
        if (accept == null)
            accept = "text/html"; // 기본값 설정

        List<MemberDTO> memberDTOs = adminMemberService.getAllMembers();

        memberDTOs = memberDTOs.stream().filter(e -> !e.getMemRoles().contains("ROLE_ADMIN")).toList(); // 관리자 계정 제외

        String[] recentLogs = new AdminLogService().getRecentLogs(30);

        req.setAttribute("recentLogs", recentLogs);
        if (accept.contains("text/html")) {
            req.setAttribute("memberList", memberDTOs);
            String lvn = "/admin/member-list";
            resolver.resolveViewName(lvn, req, resp);
        } else if (accept.contains("application/json")) {
            resp.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                Gson gson = new Gson();
                String json = gson.toJson(memberDTOs);
                out.print(json);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "지원하지 않는 Accept 타입입니다. " + accept);
        }
    }
}
