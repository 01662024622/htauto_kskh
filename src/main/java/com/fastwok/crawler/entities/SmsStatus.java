package com.fastwok.crawler.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="ht60_sms_api")
public class SmsStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "table_name")
    private String table;
    @Column(name = "code")
    private String code;
    @Column(name = "phone")
    private String phone;
    @Column(name = "flag")
    private Long flag;
    @Column(name = "status",length = 10000)
    private String status;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public SmsStatus(String table, String code, String phone, Long flag, String status){
        this.table=table;
        this.code=code;
        this.phone=phone;
        this.flag=flag;
        this.status=status;
    }
}
