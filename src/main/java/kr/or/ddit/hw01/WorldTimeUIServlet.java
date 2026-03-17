package kr.or.ddit.hw01;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@WebServlet("/hw01/worldtime/ui")
public class WorldTimeUIServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 1.

        Locale[] locales = Locale.getAvailableLocales();
        Set<String> zoneSet = ZoneId.getAvailableZoneIds();

        // 2.
        Map<String, String> localeMap = new HashMap<>();
        for (Locale locale : locales) {
            String languageTag = locale.toLanguageTag();
            String displayName = locale.getDisplayLanguage(locale);
            if (displayName.isBlank())
                continue;
            localeMap.put(languageTag, displayName);
        }

        Map<String, String> timeZoneMap = new HashMap<>();
        timeZoneMap.putAll(ZoneId.getAvailableZoneIds().stream()
                .collect(Collectors.toMap(
                        z -> z,
                        z -> ZoneId.of(z).getDisplayName(TextStyle.FULL, Locale.getDefault()))));
        // ====================UI 작업===========================

        // 3. map -> option 생성
        StringBuffer localeOptions = new StringBuffer();
        for (Entry<String, String> entry : localeMap.entrySet()) {
            String languageTag = entry.getKey();
            String name = entry.getValue();
            localeOptions.append("<option value='%s'>%s</option>".formatted(languageTag, name));

        }
        StringBuffer zoneOptions = new StringBuffer();
        for (Entry<String, String> entry : timeZoneMap.entrySet()) {
            String languageTag = entry.getKey();
            String name = entry.getValue();
            zoneOptions.append("<option value='%s'>%s</option>".formatted(languageTag, name));

        }

        // 4. html 생성
        String template = """
                <!doctype html>
                <html lang="ko">
                    <head>
                    <meta charset="UTF-8" />
                    <title>World Time Service</title>
                    </head>
                    <body>
                    <h1>세계 시간 서비스</h1>
                    <form method="GET" action="../../hw01/worldtime">
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

                    </body>
                </html>

                """;
        String content = template.formatted(localeOptions, zoneOptions);
        String mime = "text/html;encoding=UTF-8";
        response.setContentType(mime);
        try (PrintWriter out = response.getWriter();) {
            out.print(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}