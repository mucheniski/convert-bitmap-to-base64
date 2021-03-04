package com.example.convertbitmaptobase64.domain.service;

import com.example.convertbitmaptobase64.application.exception.GeneralApiException;
import com.example.convertbitmaptobase64.domain.domain.Account;
import com.example.convertbitmaptobase64.domain.exception.EntityNotFoundException;
import com.example.convertbitmaptobase64.domain.port.AccountRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private Configuration freeMarkerConfiguration;

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found with id " + id));
    }

    public String getStatementString(Long id) {
        Account account = findById(id);
        return processTemplate(account);
    }

    public String convertAndEncode(Long id) {
        try {
            String pageHtml = getStatementString(id);
            BufferedImage bufferedImage = convertToBufferedImage(pageHtml);
            return encodeImageToBase64(bufferedImage);
        } catch (Exception e) {
            throw new GeneralApiException("Error to convert to and decode ", e);
        }
    }

    public BufferedImage convertToBufferedImage(String pageHtml) {
        try {
            Path tempFile = Files.createTempFile("sampleFile","html");
            FileUtils.writeStringToFile(tempFile.toFile(), pageHtml, StandardCharsets.UTF_8.name());
            Java2DRenderer renderer = new Java2DRenderer(tempFile.toFile(), 398, 712);
            return renderer.getImage();
        } catch (Exception e) {
            throw new GeneralApiException("Error to convert to BufferedImage ", e);
        }
    }

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

    public void decodeBase64ToImageAndSaveFile() throws IOException {

        FileInputStream inputStream = new FileInputStream("C:\\ws-developer\\convert-bitmap-to-base64\\src\\main\\resources\\images\\testencodebase64.txt");
        byte[] bytesTxt = inputStream.readAllBytes();
        byte[] bytes64 = Base64.getDecoder().decode(bytesTxt);

        // Export file
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\ws-developer\\convert-bitmap-to-base64\\src\\main\\resources\\images\\output.bmp");
        fileOutputStream.write(bytes64);
        fileOutputStream.close();

        inputStream.close();

    }

    private String processTemplate(Account account) {
        try {
            Template template = freeMarkerConfiguration.getTemplate("statement.html");
            HashMap<String, Object> model = new HashMap<>();
            model.put("account", account);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new GeneralApiException("Error to generate a template string ", e);
        }
    }

    // convert BufferedImage to byte[]
    private byte[] toByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    // convert byte[] to BufferedImage
    private BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return bufferedImage;
    }

}
