package com.example.convertbitmaptobase64.domain.service;

import com.example.convertbitmaptobase64.domain.domain.Account;
import com.example.convertbitmaptobase64.domain.exception.EntityNotFoundException;
import com.example.convertbitmaptobase64.domain.port.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found with id " + id));
    }

}
