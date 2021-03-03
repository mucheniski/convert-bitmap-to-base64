package com.example.convertbitmaptobase64.domain.port;

import com.example.convertbitmaptobase64.domain.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}