package kr.or.ddit.auth;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.auth.service.AuthenticateService;
import kr.or.ddit.member.dto.MemberDTO;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();
    private AuthenticateService authenticateService = new AuthenticateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");

        if (message != null) {
            req.setAttribute("message", message);
            session.removeAttribute("message");
        }

        String lvn = "login/loginForm";
        viewResolver.resolveViewName(lvn, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String lvn = null;

        if (session == null || session.isNew()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 1. Character Encoding Filter에서 인코딩 처리 -> 특수 문자 깨짐 방지
        req.setCharacterEncoding("UTF-8");

        // 2. 파라미터 수신
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 3. 파라미터 검증 : 누락시 로그인 폼으로 redirect, message 전달

        // if (username == null || password == null | username.trim().isBlank() ||
        // password.trim().isBlank()) {
        // session.setAttribute("message", "Id 또는 Password 누락");
        // viewResolver.resolveViewName("redirect:/login", req, resp);
        // return;
        // }

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            lvn = "redirect:/login";
            session.setAttribute("message", "Id 또는 Password 누락");
            viewResolver.resolveViewName(lvn, req, resp);
            return;
        }

        // 4. 인증 (AuthenticateService 의존관계)
        MemberDTO memberDTO = authenticateService.authenticate(username, password);

        // 5. 인증 성공 -> welcome 페이지 redirect
        // 인증 실패 -> 로그인 폼으로 redirect, 인증 실패 이유 message
        if (memberDTO == null) {
            session.setAttribute("message", "Id 또는 Password 불일치");
            viewResolver.resolveViewName("redirect:/login", req, resp);
            return;
        }

        // 임시로 session에 로그인 여부 추가
        session.setAttribute("authMember", memberDTO);
        viewResolver.resolveViewName("redirect:/index.do", req, resp);
    }
}
