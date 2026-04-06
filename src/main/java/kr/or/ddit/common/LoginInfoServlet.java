package kr.or.ddit.common;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.member.dto.MemberDTO;

@WebServlet("/loginInfo")
public class LoginInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MemberDTO authMember = (MemberDTO) req.getSession().getAttribute("authMember");
        System.out.println("LoginInfoServlet - authMember: " + authMember);
        resp.setContentType("application/json; charset=UTF-8");
        if (authMember == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Map<String, Object> loginInfo = new LinkedHashMap<>();
            loginInfo.put("memId", authMember.getMemId());
            loginInfo.put("memName", authMember.getMemName());
            loginInfo.put("memRoles", authMember.getMemRoles());

            Gson gson = new Gson();
            try (var out = resp.getWriter()) {
                gson.toJson(loginInfo, out);
            }
        }
    }
}
