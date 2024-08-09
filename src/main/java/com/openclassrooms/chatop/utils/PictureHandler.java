package com.openclassrooms.chatop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PictureHandler {
    public static String downloadPicture(String imageUrl) throws IOException {

        // Extracts file name.
        String[] sections = imageUrl.split("/");
        String fileName = sections[sections.length - 1]; // Get the last part of the URL as file name

        // Downloads picture.
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();

        Files.copy(inputStream, Paths.get("src/main/resources/pictures", fileName), StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();

        return Paths.get("src/main/resources/pictures", fileName).toString();
    }
}