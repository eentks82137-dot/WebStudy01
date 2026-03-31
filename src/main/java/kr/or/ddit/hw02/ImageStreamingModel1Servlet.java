package kr.or.ddit.hw02;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hw02/image")
public class ImageStreamingModel1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String directory = "/home/san02/devf/medias/images/";
        String fileName = req.getParameter("imageName");

        if (fileName == null || fileName.trim().isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "필수 파라미터 누락");
            return;
        }

        Path imagePath = Paths.get(directory, fileName);

        if (!Files.exists(imagePath)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "%s 이미지 없음".formatted(fileName));
            return;
        }

        Cookie cookie = new Cookie("lastImage", fileName);
        cookie.setMaxAge(60 * 60 * 24 * 3); // 3 days
        cookie.setPath("/"); // 모든 경로에서 접근 가능하도록 설정
        resp.addCookie(cookie);
        ServletContext application = getServletContext();
        String mime = application.getMimeType(fileName);
        resp.setContentType(mime);
        try (OutputStream out = resp.getOutputStream();) {
            Files.copy(imagePath, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
