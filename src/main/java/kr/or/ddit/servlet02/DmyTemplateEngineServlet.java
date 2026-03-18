package kr.or.ddit.servlet02;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("*.dmy")
public class DmyTemplateEngineServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. 템플릿 읽기: 요청 URL에 해당하는 .shr 파일을 읽어 문자열로 반환
            String template = readTemplate(request);
            System.out.println(template);

            // 2. 데이터 결합: 템플릿 내 @key@ 패턴을 request 속성의 값으로 치환
            String html = mergeData(template, request);

            // 3. 응답 전송: Content-Type 설정 후 치환된 HTML을 응답
            sendResponse(response, html);

        } catch (FileNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private String readTemplate(HttpServletRequest request) throws IOException {
        // 요청 URI에서 .dmy 파일 경로 추출
        String servletPath = request.getServletPath();
        try (
                // ServletContext.getResourceAsStream()으로 템플릿 파일을 읽기 위한 스트림 개방
                InputStream is = getServletContext().getResourceAsStream(servletPath);) {
            if (is == null)
                throw new FileNotFoundException(servletPath);
            // 파일 내용을 String으로 반환
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // @key@ 에서 key 에 해당하는 속성명을 추출하기 위한 정규식
    private static final Pattern REGEXP = Pattern.compile("@(\\w+)@");

    private String mergeData(String template, HttpServletRequest request) {
        // 정규식으로 템플릿 탐색
        Matcher matcher = REGEXP.matcher(template);
        StringBuilder content = new StringBuilder();
        while (matcher.find()) {
            // key 에 해당하는 속성명 추출
            String key = matcher.group(1);
            // 해당 속성명으로 scope 를 통해 전달한 속성의 값 조회
            Object value = request.getAttribute(key);
            // @key@ 에 해당하는 플레이스홀더를 실제 전달된 값으로 치환
            matcher.appendReplacement(content, Matcher.quoteReplacement(Objects.toString(value, "")));
        }
        // 치환이 완료된후, 템플릿 파일의 나머지 문자열 가져오기
        matcher.appendTail(content);
        // 치환 완료된 문자열 반환
        return content.toString();
    }

    private void sendResponse(HttpServletResponse response, String html)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(html);
    }
}