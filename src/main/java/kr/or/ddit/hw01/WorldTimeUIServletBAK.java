package kr.or.ddit.hw01;

import java.io.IOException;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hw01/worldtime/uiBAK")
public class WorldTimeUIServletBAK extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sbLocale = new StringBuilder();
        StringBuilder sbTz = new StringBuilder();

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

        Locale[] locales = Locale.getAvailableLocales();

        Map<String, String> localeMap = new HashMap<>();
        for (Locale local2 : locales) {
            String languageTag = local2.toLanguageTag();
            String displayName = local2.getDisplayLanguage(local2);
            if (displayName.isBlank())
                continue;
            sbLocale.append("<option value='%s' %s>%s - %s</option>".formatted(languageTag,
                    languageTag.equals(locale.toLanguageTag()) ? "selected" : "", displayName, languageTag));
            localeMap.put(languageTag, displayName);
        }

        ZoneId.getAvailableZoneIds().stream().sorted()
                .forEach(n -> {
                    sbTz.append("<option value='%s' %s>%s - %s</option>".formatted(n,
                            n.equals(zone.getId()) ? "selected" : "",
                            n, ZoneId.of(n).getDisplayName(TextStyle.FULL, Locale.getDefault())));
                });
        String template = """
                        <!doctype html>
                        <html lang="ko">
                            <head>
                            <meta charset="UTF-8" />
                            <title>World Time Service</title>
                            </head>
                            <body>
                            <h1>세계 시간 서비스</h1>
                            <form method="GET" action="../../../hw01/worldtime/uiBAK">
                                <label for="locale">로케일:</label>
                                <select name="locale" id="locale">
                                %s
                                </select>
                                <br /><br />
                                <label for="timezone">타임존:</label>
                                <select name="timezone" id="timezone">
                                %s
                                </select>
                                <br /><br />
                                <button type="submit">시간 확인 (Sync)</button>
                            </form>
                            <div id="result">
                            <h1>세계 시간</h1>
                                <p>locale: %s</p>
                                <p>zone: %s</p>
                                <p>%s</p>
                            </div>
                            </body>
                        </html>
                """;
        String content = String.format(template, sbLocale.toString(), sbTz.toString(), locale, zone, formatted

        );
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(content);
    }

}
