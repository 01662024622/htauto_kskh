package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="ht50_revenues")
public class Revenues {
    @Id
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "coin")
    private Integer coin;
    @Column(name = "level")
    private String level;
}
