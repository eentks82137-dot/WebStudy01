package kr.or.ddit.hw02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/hw02/worldtime/ui")
public class WorldTimeModel2UIServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        // 1.

        Locale[] locales = Locale.getAvailableLocales();

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
                        z -> ZoneId.of(z).getDisplayName(TextStyle.FULL, request.getLocale()))));

        request.setAttribute("localeMap", localeMap);
        request.setAttribute("timeZoneMap", timeZoneMap);
        String view = "/WEB-INF/views/hw02/world-time-ui.jsp";
        request.getRequestDispatcher(view).forward(request, response);
    }
}