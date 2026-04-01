package kr.or.ddit.hw07;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.hw07.service.GetMediaService;

@WebServlet("/hw07/media-list.json")
public class MediaViewerServlet extends HttpServlet {

    /**
     * 미디어 파일 목록을 JSON 형식으로 반환하는 메서드
     * 
     * @param req  HTTP 요청 객체
     * @param resp HTTP 응답 객체
     * @throws ServletException 서블릿 처리 중 발생하는 예외
     * @throws IOException      입출력 처리 중 발생하는 예외
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> imageList = GetMediaService.getImageList();
        List<String> videoList = GetMediaService.getVideoList();
        List<String> textList = GetMediaService.getTextList();

        Gson gson = new Gson();
        Map<String, List<String>> mediaMap = Map.of(
                "images", imageList,
                "videos", videoList,
                "texts", textList);
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("application/json");
            gson.toJson(mediaMap, out);
        }
    }
}
