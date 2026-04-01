package kr.or.ddit.servlet04;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.mvc.ViewResolverComposite;

@WebServlet("/04/stateCreate")
public class StateCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String logicalViewName = "/04/state-form";
        new ViewResolverComposite().resolveViewName(logicalViewName, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 1. request nickname : 요청에 저장된 %s
        // 2. session nickname : 세션에 저장된 %s
        // 3. cookie nickname : 쿠키에 저장된 %s, 최대 7일 동안 유지
        // /04/stateRead로 리다이렉트

        String nickname = req.getParameter("nickname");

        if (StringUtils.isBlank(nickname)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "nickname은 필수 입력");
            return;
        }
        String cookieValue = URLEncoder.encode("쿠키에 저장된 %s".formatted(nickname), "UTF-8");

        HttpSession session = req.getSession();
        req.setAttribute("nickname", "요청에 저장된 %s".formatted(nickname));
        session.setAttribute("nickname", "세션에 저장된 %s".formatted(nickname));
        Cookie cookie = new Cookie("nickname", cookieValue);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath(req.getContextPath());

        resp.addCookie(cookie);

        String logicalViewName = "redirect:/04/stateRead";
        new ViewResolverComposite().resolveViewName(logicalViewName, req, resp);
    }
}
