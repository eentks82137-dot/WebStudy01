package kr.or.ddit.hw01;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/hw01/worldtime/options")
public class LocaleTimeZoneJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");

        Locale[] locales = Locale.getAvailableLocales();

        Map<String, String> localeMap = new HashMap<>();
        for (Locale local2 : locales) {
            String languageTag = local2.toLanguageTag();
            String displayName = local2.getDisplayLanguage(local2);
            if (displayName.isBlank())
                continue;
            localeMap.put(languageTag, displayName);
        }

        Map<String, String> timeZoneMap = new HashMap<>();
        timeZoneMap.putAll(ZoneId.getAvailableZoneIds().stream().filter(e -> !e.contains("+"))
                .collect(Collectors.toMap(
                        z -> z,
                        z -> ZoneId.of(z).getDisplayName(TextStyle.FULL, Locale.getDefault()))));

        Map<String, Map<String, String>> target = Map.of("locales", localeMap, "timezones", timeZoneMap);

        String json = new Gson().toJson(target);

        String mime = "application/json;encoding=UTF-8";
        resp.setContentType(mime);
        try (PrintWriter out = resp.getWriter();) {
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
