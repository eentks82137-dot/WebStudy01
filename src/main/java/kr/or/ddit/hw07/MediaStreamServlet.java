package kr.or.ddit.hw07;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.hw07.service.GetMediaService;

@WebServlet("/hw07/media")
public class MediaStreamServlet extends HttpServlet {

    /**
     * 미디어 파일을 스트리밍으로 전송하는 메서드
     * 
     * @param req  HTTP 요청 객체
     * @param resp HTTP 응답 객체
     * @throws ServletException 서블릿 처리 중 발생하는 예외
     * @throws IOException      입출력 처리 중 발생하는 예외
     */
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

        // 비디오 파일인 경우 Range 헤더를 지원하여 스트리밍으로 전송
        if (GetMediaService.getVideoList().contains(filename)) {
            handleVideo(filename, req, resp);
            return;
        }

        ServletContext application = getServletContext();
        String mime = application.getMimeType(filename);
        if (mime == null) {
            mime = "application/octet-stream"; // 기본값
        }
        resp.setContentType(mime);
        try (OutputStream out = resp.getOutputStream();) {
            Files.copy(GetMediaService.getPath(filename), out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 비디오 파일을 스트리밍으로 전송하는 메서드
     * 
     * @param filename 파일 이름
     * @param req      HTTP 요청 객체
     * @param resp     HTTP 응답 객체
     * @throws IOException 파일 처리 중 발생하는 예외
     */
    private void handleVideo(String filename, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Path filePath = GetMediaService.getPath(filename);

        ServletContext application = getServletContext();
        String mime = application.getMimeType(filename);
        if (mime == null) {
            mime = "application/octet-stream"; // 기본값
        }
        File file = filePath.toFile();
        long length = file.length();
        long start = 0;
        long end = length - 1;

        String range = req.getHeader("Range");
        boolean isPart = false;

        if (range != null && range.startsWith("bytes=")) {
            // 예: bytes=1024-2047
            String[] ranges = range.substring("bytes=".length()).split("-");

            try {
                if (ranges.length > 0 && !ranges[0].isEmpty()) {
                    // 시작 바이트 위치
                    start = Long.parseLong(ranges[0]);
                }
                if (ranges.length > 1 && !ranges[1].isEmpty()) {
                    // 종료 바이트 위치
                    end = Long.parseLong(ranges[1]);
                }
                isPart = true;
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }
        }

        long contentLength = end - start + 1; // 클라이언트에게 전송할 콘텐츠 길이 계산
        resp.setContentType(mime);
        resp.setHeader("Accept-Ranges", "bytes");

        if (isPart) {
            resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 부분 콘텐츠 응답
            resp.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + length); // Content-Range 헤더 설정
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        resp.setHeader("Content-Length", String.valueOf(contentLength)); // Content-Length 헤더 설정

        try (RandomAccessFile raf = new RandomAccessFile(file, "r"); // 파일을 읽기 모드로 열고, try-with-resources로 자동으로 닫히도록 함
                OutputStream out = resp.getOutputStream()) {
            raf.seek(start); // 파일 포인터를 시작 위치로 이동

            byte[] buffer = new byte[8192]; // 8KB 버퍼
            long bytesToRead = contentLength; // 남은 바이트 수
            int read; // 실제로 읽은 바이트 수

            while (bytesToRead > 0 // 아직 읽어야 할 바이트가 남아있고, 파일에서 읽을 수 있는 데이터가 있을 때
                    &&
                    (read = raf.read(buffer, 0, (int) Math.min(buffer.length, bytesToRead))) != -1) // raf.read()는 읽은
                                                                                                    // 바이트 수를 반환하며, 더 이상
                                                                                                    // 읽을 데이터가 없으면 -1을
                                                                                                    // 반환

            {
                out.write(buffer, 0, read);
                bytesToRead -= read; // 남은 바이트 수 감소

            }

        } catch (Exception e) {
            // 클라이언트가 연결을 끊었거나, 파일 읽기/쓰기 중 오류가 발생한 경우 예외 처리
            // ClientAbortException이 발생할 수 있으므로 적절히 무시하거나 로깅
            System.err.println("파일 스트리밍 중 오류 발생: " + e.getMessage());
        }
    }

}
