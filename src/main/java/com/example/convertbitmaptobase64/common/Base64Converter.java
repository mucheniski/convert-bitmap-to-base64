package com.example.convertbitmaptobase64.common;

import com.example.convertbitmaptobase64.application.exception.GeneralApiException;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
public class Base64Converter {

    @Value("${default.image-templates-path}")
    private String defaultImageTemplatesPath;

    public String encodeImageToBase64(BufferedImage bufferedImage) throws IOException {
        byte[] bytes = toByteArray(bufferedImage, "bmp");
        String imageString = Base64.getEncoder().encodeToString(bytes);
        String filePath = defaultImageTemplatesPath + "testencodebase64.txt";
        // Write base64 in file if you need
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(imageString);
        fileWriter.close();
        decodeBase64ToImageAndSaveFile(filePath);
        return imageString;
    }

    public void decodeBase64ToImageAndSaveFile(String pathFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(pathFile);
        byte[] bytesTxt = inputStream.readAllBytes();
        byte[] bytes64 = Base64.getDecoder().decode(bytesTxt);
        // Export file
        FileOutputStream fileOutputStream = new FileOutputStream(defaultImageTemplatesPath + UUID.randomUUID().toString() + ".bmp");
        fileOutputStream.write(bytes64);
        fileOutputStream.close();
        inputStream.close();
    }

    // convert BufferedImage to byte[]
    private byte[] toByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        return baos.toByteArray();
    }

    // convert byte[] to BufferedImage
    private BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return bufferedImage;
    }

}
