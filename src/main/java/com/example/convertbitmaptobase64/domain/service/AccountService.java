package com.example.convertbitmaptobase64.domain.service;

import com.example.convertbitmaptobase64.application.exception.GeneralApiException;
import com.example.convertbitmaptobase64.domain.domain.Account;
import com.example.convertbitmaptobase64.domain.exception.EntityNotFoundException;
import com.example.convertbitmaptobase64.domain.port.AccountRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

}
