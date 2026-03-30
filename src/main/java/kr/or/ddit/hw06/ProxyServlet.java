package kr.or.ddit.hw06;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hw06/proxy-headers")
public class ProxyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String target = req.getParameter("target");
        if (target == null || target.trim().isBlank()) {
            resp.sendError(400, "파라미터 누락");
            return;
        }
        System.out.println(target);
        URI uri = null;
        try {
            uri = new URI(target);
        } catch (IllegalArgumentException | URISyntaxException e) {
            resp.sendError(400, "target URI 형식 오류");
            return;
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String html = response.body();
            // 커스텀 스타일 주입
            String cssHref = req.getContextPath() + "/resources/css/mdn.css";
            html = html.replace("</head>",
                    "<link rel=\"stylesheet\" href=\"" + cssHref + "\"></head>");

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.print(html);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "외부 API 요청이 중단되었습니다.");
            throw new RuntimeException(e);
        }
    }
}
