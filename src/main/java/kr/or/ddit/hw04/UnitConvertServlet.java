package kr.or.ddit.hw04;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.hw04.domain.Unit;
import kr.or.ddit.hw04.domain.UnitType;
import kr.or.ddit.hw04.dto.ConversionRequest;
import kr.or.ddit.hw04.dto.ErrorResponse;
import kr.or.ddit.hw04.exception.UnitConversionException;
import kr.or.ddit.hw04.service.UnitConversionService;
import kr.or.ddit.hw04.validation.ConversionValidator;

@WebServlet("/hw04/convert")
public class UnitConvertServlet extends HttpServlet {
    private final UnitConversionService conversionService = new UnitConversionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object target = session.getAttribute("target1");
        Object error = session.getAttribute("error1");

        if (target != null) {
            req.setAttribute("target1", target);
            session.removeAttribute("target1");
        }

        if (error != null) {
            req.setAttribute("error1", error);
            session.removeAttribute("error1");
        }

        Map<UnitType, List<Unit>> unitGroup = Arrays.stream(Unit.values())
                .collect(Collectors.groupingBy(unit -> unit.getType()));
        req.setAttribute("unitGroup", unitGroup);
        String view = "/WEB-INF/views/hw04/convert.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int status = 200;
        // 1. 파라미터 수신 (value, from, to)
        String value = req.getParameter("value");
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        ConversionRequest reqDTO = null;
        Object nativeTarget = null;

        if (value == null || from == null || to == null ||
                value.trim().isBlank() || from.trim().isBlank() || to.trim().isBlank()) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "파라미터가 누락되었습니다. from: %s, to: %s, value: %s".formatted(from, to, value),
                    reqDTO);
            sendError(req, resp, errorResponse);
            return;
        }
        Locale locale = req.getLocale();

        // 2. 요청 검증 후 requestDTO로 반환
        try {
            reqDTO = ConversionValidator.validate(value, from, to);

            // 3. 실제 변환 로직 사용, reqDTO를 넘기고 respDTO를 반환
            nativeTarget = conversionService.convert(reqDTO, locale);

            // 검증 실패시 예외를 캐치하고 400에러로 반환

        } catch (UnitConversionException unitConversionException) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            ErrorResponse errorResponse = new ErrorResponse(status, unitConversionException.getMessage(), reqDTO);
            sendError(req, resp, errorResponse);
            return;
        } catch (RuntimeException runtimeException) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            ErrorResponse errorResponse = new ErrorResponse(status, runtimeException.getMessage(), reqDTO);
            sendError(req, resp, errorResponse);
            return;
        }
        String accept = req.getHeader("Accept");
        if (accept != null && accept.contains("json")) {
            handleJson(nativeTarget, resp, req);
            return;
        } else if (accept != null && accept.contains("html")) {
            handleHtml(nativeTarget, resp, req);
            return;
        } else {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "지원하지 않는 Accept 헤더입니다. accept: %s".formatted(accept),
                    reqDTO);
            sendError(req, resp, errorResponse);
            return;
        }
    }

    private void sendError(HttpServletRequest req, HttpServletResponse resp, ErrorResponse errorResponse)
            throws IOException {
        String accept = req.getHeader("Accept");

        if (accept != null && accept.contains("json")) {
            resp.setStatus(errorResponse.getStatusCode());
            resp.setContentType("application/json;charset=UTF-8");
            new Gson().toJson(errorResponse, resp.getWriter());
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("error1", errorResponse.getMessage());
        String view = "/hw04/convert";
        resp.sendRedirect(view);
    }

    private void handleJson(Object nativeTarget, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        new Gson().toJson(nativeTarget, resp.getWriter());
    }

    private void handleHtml(Object nativeTarget, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute("target1", nativeTarget);
        String view = "/hw04/convert";
        resp.sendRedirect(view);
    }
}
