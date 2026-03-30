package kr.or.ddit.hw05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.hw05.dto.ExchangeRequestDTO;
import kr.or.ddit.hw05.dto.ExchangeResponseDTO;
import kr.or.ddit.hw05.service.ExchangeService;
import kr.or.ddit.hw05.validation.ExchangeValidator;

@WebServlet("/hw05/exchange")
public class ExchangeServlet extends HttpServlet {
    private ExchangeService service = new ExchangeService();
    private ExchangeValidator validator = new ExchangeValidator();
    private Gson gson = new Gson();
    private final String MODEL_NAME = "resultDTO";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        ExchangeResponseDTO responseDTO = (ExchangeResponseDTO) session.getAttribute(MODEL_NAME);
        if (responseDTO != null) {
            req.setAttribute(MODEL_NAME, responseDTO);
            session.removeAttribute(MODEL_NAME); // flash attribute
        }

        List<Currency> currencies = service.getConvertibleCurrencies();
        req.setAttribute("currencies", currencies);
        String view = "/WEB-INF/views/hw05/exchange.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }

    private ExchangeRequestDTO getDTOFromParameters(HttpServletRequest req) {
        return validator.validate(
                req.getParameter("amount"),
                req.getParameter("from"),
                req.getParameter("to"));
    }

    private ExchangeRequestDTO getDTOFromJsonBody(HttpServletRequest req) {
        try {
            return gson.fromJson(req.getReader(), ExchangeRequestDTO.class);
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            throw new IllegalArgumentException(e);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("잘못된 JSON 형식입니다.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Content-Type, Accept 확인
        String contentType = Optional.ofNullable(
                req.getContentType()).orElse("application/x-www-form-urlencoded");
        String accept = Optional.ofNullable(
                req.getHeader("Accept")).orElse("text/html");
        int status = 200;
        String message = "";

        ExchangeRequestDTO requestDTO = null;
        // 2. 파라미터 or Json 수신
        // 3. 검증 -> ExchangeRequestDTO 객체로 변환
        try {
            if (contentType.contains("json")) {
                requestDTO = getDTOFromJsonBody(req);
            } else if (contentType.contains("x-www-form-urlencoded")) {
                requestDTO = getDTOFromParameters(req);
            } else {
                status = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
                message = "%s 는 지원하지 않는 Content-Type입니다.".formatted(contentType);
            }

            if (status == 200) {
                // 4. 변환(service.exchange()) -> ExchangeResponseDTO
                ExchangeResponseDTO responseDTO = service.exchange(requestDTO, req.getLocale());

                // 5. 응답 처리 -> Accept 헤더에 따라 JSON or HTML
                if (accept.contains("json")) {
                    handleJson(responseDTO, resp);
                    return;
                } else if (accept.contains("html")) {
                    handleHtml(responseDTO, req, resp);
                    return;
                } else {
                    status = HttpServletResponse.SC_NOT_ACCEPTABLE;
                    message = "%s 는 지원하지 않는 Accept 헤더입니다.".formatted(accept);
                }
            }

        } catch (IllegalArgumentException e) {
            // 3-1. 검증 실패 -> 400 + 에러 메시지
            status = HttpServletResponse.SC_BAD_REQUEST;
            message = e.getMessage();
        }

        if (accept.contains("json")) {
            resp.setStatus(status);
            Object errorResp = Map.of("status", status, "message", message);
            handleJson(errorResp, resp);
        } else {
            resp.sendError(status, message);
        }
    }

    private void handleJson(Object nativeTarget, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = resp.getWriter()) {
            gson.toJson(nativeTarget, writer);
        }
    }

    private void handleHtml(ExchangeResponseDTO responseDTO, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // req.setAttribute("responseDTO", responseDTO);
        // String view = "/WEB-INF/views/hw05/exchange.jsp";
        // req.getRequestDispatcher(view).forward(req, resp);

        req.getSession().setAttribute(MODEL_NAME, responseDTO);
        String location = req.getContextPath() + "/hw05/exchange";
        resp.sendRedirect(location);
    }
}
