package com.example.convertbitmaptobase64.domain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jbosslog.JBossLog;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Account {

    @EqualsAndHashCode.Include
    @Id
    private Long id;

    private String logo;
    private String name;
    private String bankname;
    private String agency;
    private String number;
    private BigDecimal balance;

    @Lob
    private Blob imglogo;

    @JsonIgnore
    public Blob getImgLogo() {
        return imglogo;
    }

}

