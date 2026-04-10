package kr.or.ddit.member.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.auth.exception.AuthenticationException;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/member/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();
    private MemberServiceImpl memberServiceImpl = new MemberServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lvn = "member/changePassword";

        viewResolver.resolveViewName(lvn, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 검증
        // old,new,confirm 누락시 bad request
        // new confirm 검증 실패시 message와 changePassword redirect
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        HttpSession session = req.getSession();

        String validateResult = validatePassword(oldPassword, newPassword, confirmPassword);
        if (validateResult != null) {
            session.setAttribute("message", validateResult);
            viewResolver.resolveViewName("redirect:/member/changePassword", req, resp);
            return;
        }

        // 2. 비밀번호 수정 로직
        // Principal에서 username 꺼내옴

        String username = req.getUserPrincipal().getName();
        try {
            // 3. 성공 logout으로 redirect
            memberServiceImpl.changePassword(username, oldPassword, newPassword);
            // session.setAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            viewResolver.resolveViewName("redirect:/logout", req, resp);
            return;
        } catch (AuthenticationException e) {
            // 4. 실패 try ~ catch AuthenticationException message와 changePassword redirect
            session.setAttribute("message", e.getMessage());
            viewResolver.resolveViewName("redirect:/member/changePassword", req, resp);
            return;
        }

    }

    /**
     * 비밀번호 검증 메서드
     * 
     * @param oldPassword     현재 비밀번호
     * @param newPassword     새 비밀번호
     * @param confirmPassword 새 비밀번호 확인
     * @return 검증 실패시 메시지, 검증 성공시 null
     */
    private String validatePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (StringUtils.isBlank(confirmPassword)
                || StringUtils.isBlank(oldPassword)
                || StringUtils.isBlank(newPassword)) {
            return "비밀번호 입력 누락";
        }

        if (StringUtils.containsWhitespace(confirmPassword)
                || StringUtils.containsWhitespace(oldPassword)
                || StringUtils.containsWhitespace(newPassword)) {
            return "비밀번호에 공백 입력";
        }

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(newPassword);
            System.out.println(confirmPassword);
            return "비밀번호가 일치하지 않습니다.";
        }

        return null;
    }
}
