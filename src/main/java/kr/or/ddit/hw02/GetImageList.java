package kr.or.ddit.hw02;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.nio.file.Path;

public class GetImageList {
    public static List<String> getImageList() throws IOException {
        String directory = "/home/san02/devf/medias/images/";
        List<String> imageList = new ArrayList<>();
        try (Stream<Path> files = Files.list(Path.of(directory));) {
            imageList.addAll(files.map(e -> e.getFileName().toString()).toList());
        }
        return imageList;

    }
}
