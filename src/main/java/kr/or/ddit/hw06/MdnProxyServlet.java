package kr.or.ddit.hw06;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hw06/mdn")
public class MdnProxyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "https://developer.mozilla.org/ko/docs/Web/HTTP/Reference/Headers";
        URI uri = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String html = response.body();
            // 필요 시 커스텀 스타일 주입
            String cssHref = req.getContextPath() + "/resources/css/mdn.css";
            html = html.replace("</head>",
                    "<link rel=\"stylesheet\" href=\"" + cssHref + "\"></head>");

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.print(html);
            }
        } catch (InterruptedException e) {
        }
    }
}
