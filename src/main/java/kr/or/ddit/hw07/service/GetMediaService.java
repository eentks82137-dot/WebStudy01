package kr.or.ddit.hw07.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GetMediaService {

    /**
     * 파일이 저장된 디렉토리 경로
     */
    private static final String VIDEO_DIRECTORY = "/home/san02/devf/medias/movies/";
    private static final String IMAGE_DIRECTORY = "/home/san02/devf/medias/images/";
    private static final String TEXT_DIRECTORY = "/home/san02/devf/medias/texts/";

    /**
     * 이미지 파일 목록을 반환하는 메서드
     * 
     * @return 이미지 파일 목록
     * @throws IOException
     */
    public static List<String> getImageList() throws IOException {
        List<String> imageList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(IMAGE_DIRECTORY));) {
            imageList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return imageList;
    }

    /**
     * 비디오 파일 목록을 반환하는 메서드
     * 
     * @return 비디오 파일 목록
     * @throws IOException
     */
    public static List<String> getVideoList() throws IOException {
        List<String> videoList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(VIDEO_DIRECTORY));) {
            videoList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return videoList;
    }

    /**
     * 텍스트 파일 목록을 반환하는 메서드
     * 
     * @return 텍스트 파일 목록
     * @throws IOException
     */
    public static List<String> getTextList() throws IOException {
        List<String> textList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(TEXT_DIRECTORY));) {
            textList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return textList;
    }

    /**
     * 파일이 존재하는지 확인하는 메서드
     * 
     * @param filename 파일 이름
     * @return 파일이 존재하면 true, 그렇지 않으면 false
     */
    public static boolean isExist(String filename) {
        try {
            return getImageList().contains(filename) || getVideoList().contains(filename)
                    || getTextList().contains(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 파일의 경로를 반환하는 메서드
     * 
     * @param filename 파일 이름
     * @return 파일의 경로
     * @throws IOException 파일이 존재하지 않을 때 발생
     */
    public static Path getPath(String filename) throws IOException {
        if (getImageList().contains(filename)) {
            return Path.of(IMAGE_DIRECTORY, filename);
        } else if (getVideoList().contains(filename)) {
            return Path.of(VIDEO_DIRECTORY, filename);
        } else if (getTextList().contains(filename)) {
            return Path.of(TEXT_DIRECTORY, filename);
        } else {
            throw new IOException("파일을 찾을 수 없습니다.");
        }
    }

}
