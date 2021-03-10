package com.example.convertbitmaptobase64.domain.service;

import com.example.convertbitmaptobase64.application.exception.GeneralApiException;
import com.example.convertbitmaptobase64.common.Base64Converter;
import com.example.convertbitmaptobase64.common.HTMLConverter;
import com.example.convertbitmaptobase64.domain.domain.Account;
import com.example.convertbitmaptobase64.domain.exception.EntityNotFoundException;
import com.example.convertbitmaptobase64.domain.port.AccountRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private HTMLConverter htmlConverter;

    @Autowired
    private Base64Converter base64Converter;

    @Value("${default.image-templates-path}")
    private String defaultImageTemplatesPath;

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found with id " + id));
    }

    public String getStatementString(Long id) {
        Account account = findById(id);
        encodeLogoOnStatement(account);
        return htmlConverter.processTemplate(account);
    }

    public String convertAndEncode(Long id, int width, int height) {
        try {
            Account account = findById(id);
            encodeLogoOnStatement(account);
            BufferedImage bufferedImage = htmlConverter.convertToBufferedImage(account, width, height);
            return base64Converter.encodeImageToBase64(bufferedImage);
        } catch (Exception e) {
            throw new GeneralApiException("Error to convert to and decode ", e);
        }
    }

    private void encodeLogoOnStatement(Account account) {
        try {
            Path filePath = Paths.get(defaultImageTemplatesPath + "logobvsmall.png");
            byte[] data = new byte[0];
            data = Files.readAllBytes(filePath);
            String imgDataAsBase64 = Base64.getEncoder().encodeToString(data);
            account.setLogo("data:image/bmp;base64," + imgDataAsBase64);

            // Write base64 in file if you need
//            FileWriter fileWriter = new FileWriter(defaultImageTemplatesPath + "logobase64.txt");
//            fileWriter.write(imgDataAsBase64);
//            fileWriter.close();

        } catch (Exception e) {
            new GeneralApiException("Error to encode logo", e);
        }
    }

    public void decodeBase64ToBitmap(String fileName) {
        try {
            String filePath = defaultImageTemplatesPath + fileName;
            base64Converter.decodeBase64ToImageAndSaveFile(filePath);
        } catch (Exception e) {
            new GeneralApiException("Error to decode file", e);
        }
    }
}
