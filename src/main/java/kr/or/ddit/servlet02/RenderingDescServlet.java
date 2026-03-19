package kr.or.ddit.servlet02;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 웹 어플리케이션에서 사용자가 소비할 최종 UI 컨텐츠가 완성되는 엔드포인트에 따라 2가지 렌더링 구조가 있다.
 * Server Side Rendering : UI 컨텐츠가 서버 사이드에서 완성됨
 * -> UI 컨텐츠: 템플릿 + 데이터 ( 서버 사이드 )
 * 
 * Model1 vs Model2 : 책임 분리 여부에 따른 아키텍쳐
 * - Model2 구조란?
 * - Template Engine?
 * 
 * Client Side Rendering : UI 컨텐츠가 클라이언트 사이드에서 완성됨
 * -> UI 컨텐츠: 템플릿 + 데이터 ( 클라이언트 사이드 )
 */
@WebServlet("/server_info")
public class RenderingDescServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 데이터: 서버의 기본 언어와 국가 (Locale), 기본 타임존 (Timezone)

        Locale clientLocale = req.getLocale();
        Locale serverLocale = Locale.getDefault();
        String language = serverLocale.getDisplayLanguage(clientLocale);
        String country = serverLocale.getDisplayCountry(clientLocale);
        ZoneId serverTimezone = ZoneId.systemDefault();
        String timezone = serverTimezone.getDisplayName(TextStyle.FULL, clientLocale);

        // ==================================================================

        req.setAttribute("language", language);
        req.setAttribute("country", country);
        req.setAttribute("timezone", timezone);

        req.getRequestDispatcher("/01/info.jsp").forward(req, resp);
    }

}