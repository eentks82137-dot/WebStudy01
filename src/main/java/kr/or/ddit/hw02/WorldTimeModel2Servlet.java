package kr.or.ddit.hw02;

import java.io.IOException;
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

@WebServlet("/hw02/worldtime")
public class WorldTimeModel2Servlet extends HttpServlet {
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

        req.setAttribute("now", formatted);
        String view = "/WEB-INF/views/hw02/world-time.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }
}
