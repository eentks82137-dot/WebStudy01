package kr.or.ddit.hw06;

import java.io.IOException;
import java.net.CacheResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.hw06.dto.CalcRequestDTO;
import kr.or.ddit.hw06.dto.CalcResponseDTO;
import kr.or.ddit.hw06.validate.Validate;
import kr.or.ddit.hw06.service.CalcService;

@WebServlet("/hw06/calc")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if (session.getAttribute("result") != null) {
            req.setAttribute("result", session.getAttribute("result"));
            session.removeAttribute("result");
        }

        String view = "/WEB-INF/views/hw06/calculator.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        String accept = req.getHeader("Accept");

        String operator = req.getParameter("operator");
        String leftStr = req.getParameter("left");
        String rightStr = req.getParameter("right");
        CalcRequestDTO dto;
        CalcResponseDTO respDTO;
        try {
            dto = Validate.validate(operator, leftStr, rightStr);
            respDTO = new CalcService().calculate(dto);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            handleHtmlResponse(req, resp, null);
            return;
        }

        if (contentType != null && contentType.contains("application/json")) {
            // Handle JSON response
        } else if (accept != null && accept.contains("application/json")) {
            // Handle JSON response
        } else {
            handleHtmlResponse(req, resp, respDTO);
        }
    }

    private void handleHtmlResponse(HttpServletRequest req, HttpServletResponse resp, CalcResponseDTO respDTO)
            throws ServletException, IOException {
        req.setAttribute("result", respDTO.getExpression());
        HttpSession session = req.getSession();
        session.setAttribute("result", respDTO.getExpression());
        resp.sendRedirect(req.getContextPath() + "/hw06/calc");
    }
}
