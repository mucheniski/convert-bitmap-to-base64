package com.example.convertbitmaptobase64.common;

import com.example.convertbitmaptobase64.application.exception.GeneralApiException;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class Base64Converter {

    public String encodeImageToBase64(BufferedImage bufferedImage) throws IOException {
        byte[] bytes = toByteArray(bufferedImage, "bmp");
        String imageString = Base64.getEncoder().encodeToString(bytes);
        // Write base64 in file if you need
        FileWriter fileWriter = new FileWriter("C:\\ws-developer\\convert-bitmap-to-base64\\src\\main\\resources\\images\\testencodebase64.txt");
        fileWriter.write(imageString);
        fileWriter.close();
        decodeBase64ToImageAndSaveFile();
        return imageString;
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

    private void decodeBase64ToImageAndSaveFile() throws IOException {

        FileInputStream inputStream = new FileInputStream("C:\\ws-developer\\convert-bitmap-to-base64\\src\\main\\resources\\images\\testencodebase64.txt");
        byte[] bytesTxt = inputStream.readAllBytes();
        byte[] bytes64 = Base64.getDecoder().decode(bytesTxt);

        // Export file
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\ws-developer\\convert-bitmap-to-base64\\src\\main\\resources\\images\\output.bmp");
        fileOutputStream.write(bytes64);
        fileOutputStream.close();

        inputStream.close();

    }

}
