package com.example.microgram.Utility;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private static final String postsDir = "src/main/resources/static/images/";

    public static MultipartFile toMultipartFile(String fileName) {
        Path path = Paths.get(postsDir + fileName);
        String name = fileName;
        String originalFileName = fileName;
        String contentType = "multipart/form-data";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    public static String createFileFromMultipartFile(MultipartFile multipartFile, int postID, String username) {
        byte[] data = getBytesFromMultipartFile(multipartFile);
        String name = String.format("posts_%s_%s.jpg", username, "post_" + postID);
        File file = new File(postsDir + name);
        try(FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static void deleteImageFile(String path) {
        try {
            Files.delete(Path.of(postsDir + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBytesFromMultipartFile(MultipartFile multipartFile) {
        byte[] data = new byte[0];
        try {
            data = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}