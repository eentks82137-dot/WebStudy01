package kr.or.ddit.hw01;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * locale과 timezone이라는 파라미터로 결정된,
 * Locale과 ZoneId에 따른 세계 시간 서비스.
 * 
 * timezone=Asia/Seoul
 * locale=ko-KR, en-US, ja-JP (locale code, language tag)
 */

@WebServlet("/hw01/worldtime")
public class WorldTimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // JSR-310 (java.time 패키지) API 활용

        // 요청 파라미터로 변경
        ZoneId zone = Optional.ofNullable(req.getParameter("timezone"))
                .map(ZoneId::of)
                .filter(z -> !z.getId().isBlank())
                .orElse(ZoneId.systemDefault());

        LocalDateTime now = LocalDateTime.now(zone);

        // 요청 파라미터로 변경
        Locale locale = Optional.ofNullable(req.getParameter("locale"))
                .map(Locale::forLanguageTag)
                .filter(l -> !l.getLanguage().isBlank())
                .orElse(Locale.getDefault());

        String formatted = now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM)
                .localizedBy(locale));

        // 서버사이드 백엔드에서는 동적 응답 컨텐츠를 생성해야 하는 경우가 많음.
        // 컨텐츠의 종류는 몇가지나 존재할까?

        String html = """
                <html>
                    <head>
                        <title>세계 시간</title>
                    </head>
                    <body>
                        <h1>세계 시간</h1>
                        <p>locale: %s</p>
                        <p>zone: %s</p>
                        <p>%s</p>
                    </body>
                </html>
                """.formatted(locale, zone, formatted);
        // MIME은 컨텐츠의 종류와 인코딩 방식을 표현하는 문자열. maintype/subtype;charset=인코딩 형태로 표현됨.
        // ex) text/html;charset=UTF-8, application/json;charset=UTF-8, image/png

        String mime = "text/html;charset=UTF-8";
        resp.setContentType(mime);

        try (PrintWriter out = resp.getWriter();) {
            out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
