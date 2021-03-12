package com.example.convertbitmaptobase64.domain.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedInputStream;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccountDTO {

    private Long id;
    private String logo;
    private String name;
    private String bankname;
    private String agency;
    private String number;
    private BigDecimal balance;

}
