package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.member.dto.MemberDTO;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/admin/memberList")
public class MemberListServlet extends HttpServlet {
    private MemberService service = new MemberServiceImpl();
    private ViewResolver resolver = new ViewResolverComposite();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accept = req.getHeader("Accept");
        if (StringUtils.isBlank(accept)) {
            accept = "text/html"; // 기본값 설정
        }
        List<MemberDTO> memberList = service.readMemberList();

        if (accept.contains("text/html")) {
            req.setAttribute("memberList", memberList);
            String lvn = "member/memberList";
            resolver.resolveViewName(lvn, req, resp);
            return;
        } else if (accept.contains("application/json")) {
            resp.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            gson.toJson(memberList, resp.getWriter());
            return;
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "지원하지 않는 Accept 타입입니다. " + accept);
            return;
        }

    }
}
