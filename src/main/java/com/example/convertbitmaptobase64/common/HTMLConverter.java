package com.example.convertbitmaptobase64.common;

import com.example.convertbitmaptobase64.application.exception.GeneralApiException;
import com.example.convertbitmaptobase64.domain.domain.Account;
import com.example.convertbitmaptobase64.domain.domain.AccountDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@Service
public class HTMLConverter {

    @Autowired
    private Configuration freeMarkerConfiguration;

    public BufferedImage convertToBufferedImage(AccountDTO accountDTO, int width, int height) {
        try {
            String pageHtml = processTemplate(accountDTO);
            Path tempFile = Files.createTempFile("sampleFile","html");
            FileUtils.writeStringToFile(tempFile.toFile(), pageHtml, StandardCharsets.UTF_8.name());
            Java2DRenderer renderer = new Java2DRenderer(tempFile.toFile(), width, height);

            /*
                Teste para escrever o BufferedImage direto no arquivo
             */
//            BufferedImage image = renderer.getImage();
//            FSImageWriter imagWriter = new FSImageWriter();
//            imagWriter.write(image, "C:\\ws-developer\\convert-bitmap-to-base64\\src\\main\\resources\\static\\images\\full-image.bmp");


            return renderer.getImage();
        } catch (Exception e) {
            throw new GeneralApiException("Error to convert to BufferedImage ", e);
        }
    }

    public String processTemplate(AccountDTO accountDTO) {
        try {
            Template template = freeMarkerConfiguration.getTemplate("statement2.html");
            HashMap<String, Object> model = new HashMap<>();
            model.put("account", accountDTO);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new GeneralApiException("Error to generate a template string ", e);
        }
    }

}
