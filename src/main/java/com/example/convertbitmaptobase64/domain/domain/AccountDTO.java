package com.example.convertbitmaptobase64.domain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Lob;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;

@Getter
@Setter
public class AccountDTO {

    private Long id;

    private String logo;
    private String name;
    private String bankname;
    private String agency;
    private String number;
    private BigDecimal balance;

}
