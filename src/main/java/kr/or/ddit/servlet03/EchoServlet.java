package kr.or.ddit.servlet03;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.servlet03.dto.EchoRequest;
import kr.or.ddit.servlet03.dto.EchoResponse;
import kr.or.ddit.servlet03.service.EchoService;

/**
 * JSON을 수신(역직렬화, request DTO)하고, 가공 후 JSON을 송신(직렬화, response DTO)하는 서블릿
 */

@WebServlet("/03/echo")
public class EchoServlet extends HttpServlet {
    private EchoService echoService = new EchoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. content-type 헤더로 수신할 수 있는 요청인지 파악
        String contentType = req.getContentType();
        if (!contentType.contains("application/json")) {
            sendErrorMessage(resp, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "읽을 수 없는 본문 형식");
            return;
        }

        String accept = req.getHeader("Accept");
        if (!accept.contains("application/json")) {
            sendErrorMessage(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, "지원하지 않는 형식");
            return;
        }

        // 2. 역직렬화 JSON -> Native Object
        Gson gson = new Gson();
        EchoRequest reqDto = gson.fromJson(req.getReader(), EchoRequest.class);

        // 사용자 입력 검증
        if (reqDto == null || reqDto.getMessage() == null || reqDto.getLanguage() == null
                || reqDto.getMessage().trim().isBlank()) {
            sendErrorMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청");
            return;
        }

        // 3. 비즈니스 로직 객체 활용
        EchoResponse respDto = echoService.processEcho(reqDto);

        // 4. 직렬화 Native Object -> JSON
        resp.setContentType("application/json");
        gson.toJson(respDto, resp.getWriter());
    }

    private void sendErrorMessage(HttpServletResponse resp, int status, String message) throws IOException {
        Gson gson = new Gson();
        resp.setStatus(status);
        resp.setContentType("application/json");
        Map<String, Object> errorMap = Map.of("status", status, "message", message);
        gson.toJson(errorMap, resp.getWriter());
        return;
    }
}
