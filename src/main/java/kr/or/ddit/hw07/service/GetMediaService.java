package kr.or.ddit.hw07.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GetMediaService {
    private static final String VIDEO_DIRECTORY = "/home/san02/devf/medias/movies/";
    private static final String IMAGE_DIRECTORY = "/home/san02/devf/medias/images/";
    private static final String TEXT_DIRECTORY = "/home/san02/devf/medias/texts/";

    public static List<String> getImageList() throws IOException {
        List<String> imageList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(IMAGE_DIRECTORY));) {
            imageList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return imageList;
    }

    public static List<String> getVideoList() throws IOException {
        List<String> videoList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(VIDEO_DIRECTORY));) {
            videoList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return videoList;
    }

    public static List<String> getTextList() throws IOException {
        List<String> textList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(TEXT_DIRECTORY));) {
            textList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return textList;
    }

    public static boolean isExist(String filename) {
        try {
            return getImageList().contains(filename) || getVideoList().contains(filename)
                    || getTextList().contains(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

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
