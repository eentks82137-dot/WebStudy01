package kr.or.ddit.hw06;

import java.io.IOException;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.hw06.dto.CalcRequestDTO;
import kr.or.ddit.hw06.dto.CalcResponseDTO;
import kr.or.ddit.hw06.validate.Validate;
import kr.or.ddit.mvc.ViewResolver;
import kr.or.ddit.mvc.ViewResolverComposite;
import kr.or.ddit.hw06.service.CalcService;

@WebServlet("/hw06/calc")
public class CalculatorServlet extends HttpServlet {
    private ViewResolver viewResolver = new ViewResolverComposite();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        if (session.getAttribute("expression") != null) {
            req.setAttribute("expression", session.getAttribute("expression"));
            session.removeAttribute("expression");
        }

        String view = "/WEB-INF/views/hw06/calculator.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        String operator = null;
        String leftStr = null;
        String rightStr = null;
        CalcRequestDTO requestDTO;
        CalcResponseDTO respDTO;

        try {
            if (contentType == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendErrorResponse(req, resp, "Content-Type 헤더가 누락되었습니다.");
                return;
            }
            if (contentType.contains("text/html") || contentType.contains("parameter")
                    || contentType.contains("x-www-form-urlencoded")) {

                operator = req.getParameter("operator");
                leftStr = req.getParameter("left");
                rightStr = req.getParameter("right");

                // 파라미터로 받은 값을 DTO로 변환하고 유효성 검사를 수행한다.
                requestDTO = Validate.validate(operator, leftStr, rightStr);
            } else if (contentType.contains("json")) {
                Gson gson = new Gson();
                JsonObject requestBody = gson.fromJson(req.getReader(), JsonObject.class);
                validateJsonBody(requestBody);
                // JSON 요청을 DTO로 변환하고 유효성 검사를 수행한다.
                requestDTO = Validate.validate(gson.fromJson(requestBody, CalcRequestDTO.class));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendErrorResponse(req, resp, "지원하지 않는 Content-Type입니다.");
                return;
            }

            // 계산 수행
            respDTO = new CalcService().calculate(requestDTO);
            System.out.println("계산 결과: " + respDTO.getExpression());
            sendResponse(req, resp, respDTO);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorResponse(req, resp, e.getMessage());
            return;
        } catch (NullPointerException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorResponse(req, resp, "필수 파라미터가 누락되었습니다.");
            return;
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorResponse(req, resp, "JSON 형식이 올바르지 않습니다.");
            return;
        } catch (JsonIOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendErrorResponse(req, resp, "JSON 처리 중 오류가 발생했습니다.");
            return;
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendErrorResponse(req, resp, "서버 내부 오류가 발생했습니다.");
            return;
        }
    }

    /**
     * 클라이언트의 Accept 헤더에 따라 JSON 또는 HTML 응답을 전송하는 메서드
     * 
     * @param req
     * @param resp
     * @param respDTO
     * @throws IOException
     * @throws ServletException
     */
    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, CalcResponseDTO respDTO)
            throws IOException, ServletException {
        String acceptHeader = req.getHeader("Accept");
        String acceptBody = req.getParameter("accept");
        String accept = "";
        if (acceptHeader != null) {
            accept = acceptHeader;
        } else if (acceptBody != null) {
            accept = acceptBody;
        } else {
            accept = "html"; // 기본값을 HTML로 설정
        }

        if (accept.contains("json")) {
            handleJsonResponse(req, resp, respDTO);
            return;
        } else if (accept.contains("html")) {
            handleHtmlResponse(req, resp, respDTO);
            return;
        } else {
            handleHtmlResponse(req, resp, respDTO);
            return;
        }

    }

    /**
     * HTML 응답을 처리하는 메서드. 계산 결과를 세션에 저장하고 리다이렉트하여 결과 페이지로 이동한다.
     * 
     * @param req
     * @param resp
     * @param respDTO
     * @throws ServletException
     * @throws IOException
     */
    private void handleHtmlResponse(HttpServletRequest req, HttpServletResponse resp, CalcResponseDTO respDTO)
            throws ServletException, IOException {

        // req.setAttribute("expression", respDTO.getExpression());
        HttpSession session = req.getSession();
        session.setAttribute("expression", respDTO.getExpression());
        // resp.sendRedirect(req.getContextPath() + "/hw06/calc");
        viewResolver.resolveViewName("redirect:/hw06/calc", req, resp);
    }

    /**
     * JSON 응답을 처리하는 메서드. 계산 결과를 JSON 형식으로 클라이언트에 전송한다.
     * 
     * @param req
     * @param resp
     * @param respDTO
     * @throws IOException
     */
    private void handleJsonResponse(HttpServletRequest req, HttpServletResponse resp, CalcResponseDTO respDTO)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(respDTO);

        resp.getWriter().write(jsonResponse);
    }

    private void sendErrorResponse(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws IOException, ServletException {
        String acceptHeader = req.getHeader("Accept");
        String acceptBody = req.getParameter("accept");
        String accept = "";
        if (acceptHeader != null) {
            accept = acceptHeader;
        } else if (acceptBody != null) {
            accept = acceptBody;
        } else {
            accept = "html"; // 기본값을 HTML로 설정
        }

        if (accept.contains("json")) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(Collections.singletonMap("error", errorMessage));
            resp.getWriter().write(jsonResponse);
        } else {
            req.setAttribute("error", errorMessage);
            try {
                req.getSession().setAttribute("expression", errorMessage);
                viewResolver.resolveViewName("redirect:/hw06/calc", req, resp);
                // resp.sendRedirect(req.getContextPath() + "/hw06/calc");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * JSON 요청 본문에 계산 필수 필드가 존재하는지 검사한다.
     */
    private void validateJsonBody(JsonObject requestBody) {
        if (requestBody == null) {
            throw new IllegalArgumentException("필수 필드가 누락되었습니다.");
        }

        validateRequiredJsonField(requestBody, "operator");
        validateRequiredJsonField(requestBody, "left");
        validateRequiredJsonField(requestBody, "right");
    }

    private void validateRequiredJsonField(JsonObject requestBody, String fieldName) {
        if (!requestBody.has(fieldName)) {
            throw new IllegalArgumentException("필수 필드가 누락되었습니다.");
        }

        JsonElement value = requestBody.get(fieldName);
        if (value == null || value.isJsonNull()) {
            throw new IllegalArgumentException("필수 필드가 누락되었습니다.");
        }
    }
}
