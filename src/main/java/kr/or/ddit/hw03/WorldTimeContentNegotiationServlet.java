package kr.or.ddit.hw03;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/hw03/worldtime/ui", "/hw03/worldtime/options" })
public class WorldTimeContentNegotiationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accept = req.getHeader("Accept");
        System.out.printf("-------%s\n", accept);

        Locale[] locales = Locale.getAvailableLocales();

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
                        z -> ZoneId.of(z).getDisplayName(TextStyle.FULL, req.getLocale()))));

        // model을 컨텐츠 생성을 위한 뷰쪽으로 전달
        req.setAttribute("localeMap", localeMap);
        req.setAttribute("timeZoneMap", timeZoneMap);

        if (accept.equals("*/*") || accept == null || accept.equals("") || accept.contains("text/html")) {
            handleHtml(req, resp);
        } else if (accept.contains("application/json")) {
            handleJson(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "HTML 또는 JSON 형식의 요청만 가능합니다.");
            return;
        }
    }

    private void handleHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String view = "/WEB-INF/views/hw03/world-time-content-negotiation.jsp";
        req.getRequestDispatcher(view).forward(req, resp);
    }

    private void handleJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Enumeration<String> attrNames = req.getAttributeNames();

        Map<String, Object> myMap = new HashMap<>();
        while (attrNames.hasMoreElements()) {
            String name = attrNames.nextElement();
            Object attrValue = req.getAttribute(name);
            myMap.put(name, attrValue);
        }
        String json = new Gson().toJson(myMap);
        resp.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print(json);
        }
    }
}
