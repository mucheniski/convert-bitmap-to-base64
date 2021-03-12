package com.example.convertbitmaptobase64.application;

import com.example.convertbitmaptobase64.domain.domain.Account;
import com.example.convertbitmaptobase64.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    public List<Account> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/statement-string")
    public String getStatamentString(@PathVariable Long id) throws IOException {
        return service.getStatementString(id);
    }

    @GetMapping("/{id}/statement-convert-and-encode")
    public String convertAndEncode(@PathVariable Long id, @RequestParam int width, @RequestParam int height) {
        return service.convertAndEncode(id, width, height);
    }

    @GetMapping("/decode-base64-to-bitmap")
    public void decodeBase64ToBitmap(@RequestParam String fileName) {
        service.decodeBase64ToBitmap(fileName);
    }

    @GetMapping("/{id}/test")
    public void test(@PathVariable Long id) throws IOException {
        service.test(id);
    }

}
