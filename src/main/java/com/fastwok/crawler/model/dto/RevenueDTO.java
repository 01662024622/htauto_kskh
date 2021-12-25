package com.fastwok.crawler.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RevenueDTO {
    private long id;
    private String code;
    private int used;
    private int coin;
    private String level;
    private String phone;

    public RevenueDTO(long id, String code, int used, int coin, String level, String phone){
        this.id=id;
        this.code=code;
        this.used=used;
        this.coin=coin;
        this.level=level;
        this.phone=phone;

    }

}
