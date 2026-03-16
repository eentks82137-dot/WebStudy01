package kr.or.ddit.hw01;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/now")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // JSR-310 (java.time 패키지) API 활용
        LocalDateTime now = LocalDateTime.now();
        String formatted = now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM));

        // 서버사이드 백엔드에서는 동적 응답 컨텐츠를 생성해야 하는 경우가 많음.
        // 컨텐츠의 종류는 몇가지나 존재할까?

        String html = """
                <html>
                    <head>
                        <title>현재 시간</title>
                    </head>
                    <body>
                        <h1>현재 시간</h1>
                        <p>%s</p>
                    </body>
                </html>
                """.formatted(formatted);
        // MIME은 컨텐츠의 종류와 인코딩 방식을 표현하는 문자열. maintype/subtype;charset=인코딩 형태로 표현됨.
        // ex) text/html;charset=UTF-8, application/json;charset=UTF-8, image/png

        String mime = "text/plain;charset=UTF-8";
        resp.setContentType(mime);

        try (PrintWriter out = resp.getWriter();) {
            out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
