package kr.or.ddit.servlet04;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.mvc.ViewResolverComposite;
import kr.or.ddit.mvc.arguments.CookieValueResolver;

@WebServlet("/04/stateRead")
public class StateReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. request
        String reqNickname = (String) req.getAttribute("nickname");
        req.setAttribute("reqNickname", reqNickname);

        // 2. session
        String sessionNickname = (String) req.getSession().getAttribute("nickname");
        req.setAttribute("sessionNickname", sessionNickname);

        // 3. cookie
        String cookieNickname = new CookieValueResolver().resolveCookieValue("nickname", req);
        req.setAttribute("cookieNickname", cookieNickname);

        String logicalViewName = "/04/state-result";
        new ViewResolverComposite().resolveViewName(logicalViewName, req, resp);
    }
}
