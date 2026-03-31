package kr.or.ddit.hw07;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.hw07.service.GetMediaService;

@WebServlet("/hw07/media")
public class MediaSreamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("file");
        if (filename == null || filename.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("파일 이름이 누락되었습니다.");
            return;
        }

        if (!GetMediaService.isExist(filename)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("파일을 찾을 수 없습니다.");
            return;
        }

        ServletContext application = getServletContext();
        String mime = application.getMimeType(filename);
        resp.setContentType(mime);
        try (OutputStream out = resp.getOutputStream();) {
            Files.copy(GetMediaService.getPath(filename), out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
