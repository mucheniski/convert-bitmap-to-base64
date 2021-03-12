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
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
public class Base64Converter {

    @Value("${default.image-templates-path}")
    private String defaultImageTemplatesPath;

    public String encodeImageToBase64(BufferedImage bufferedImage) throws IOException {
        byte[] bytesBody = toByteArray(bufferedImage, "bmp");
        byte[] byteLogo = getLogoInByte(defaultImageTemplatesPath + "logobvsmall.bmp");

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        outputStream.write(bytesBody);
//        outputStream.write(bytesLogo);
//
//        byte[] allBytes = outputStream.toByteArray();

//        byte[] allBytes = joinByteArray(bytesLogo, bytesBody);

        String imageString = Base64.getEncoder().encodeToString(bytesBody);
        String filePath = defaultImageTemplatesPath + "testencodebase64.txt";

        saveFileOnDisk(filePath, imageString);
        decodeBase64ToImageAndSaveFile(filePath);

        return imageString;
    }

    private byte[] getLogoInByte(String path) throws IOException {
        Path imgLogo = Paths.get(path);
        byte[] bytesLogo = Files.readAllBytes(imgLogo);
        return bytesLogo;
    }

    public static byte[] joinByteArray(byte[] byte1, byte[] byte2) {
        return ByteBuffer.allocate(byte1.length + byte2.length)
                .put(byte1)
                .put(byte2)
                .array();
    }

    /*
        Usado para o encode do logo em Base64 e retorno em String
     */
    private String encodeLogoOnStatement() throws IOException {

            Path filePath = Paths.get(defaultImageTemplatesPath + "logobvsmall.bmp");
            byte[] data = new byte[0];
            data = Files.readAllBytes(filePath);
            String imgDataAsBase64 = Base64.getEncoder().encodeToString(data);
            return  imgDataAsBase64;

//            // Write base64 in file if you need
//            FileWriter fileWriter = new FileWriter(defaultImageTemplatesPath + "logobase64.txt");
//            fileWriter.write(imgDataAsBase64);
//            fileWriter.close();

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

    private void saveFileOnDisk(String path, String file) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(file);
        fileWriter.close();
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
