package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name="ht50_information_customer_surveys")
public class Customer {
    @Id
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "name")
    private String name;
    @Column(name = "name_gara")
    private String gara;
}
