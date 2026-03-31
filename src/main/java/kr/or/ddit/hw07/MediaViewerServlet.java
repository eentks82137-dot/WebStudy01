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
