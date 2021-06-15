package com.fastwok.crawler.job;

import com.fastwok.crawler.entities.Cskh;
import com.fastwok.crawler.entities.Customer;
import com.fastwok.crawler.entities.SmsStatus;
import com.fastwok.crawler.repository.CskhRepository;
import com.fastwok.crawler.repository.CustomerRepository;
import com.fastwok.crawler.repository.SmsStatusRepository;
import com.fastwok.crawler.services.isservice.SmsService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.persistence.Table;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class CrawlerFwSchedule {
    @Autowired
    SmsService smsService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CskhRepository cskhRepository;
    @Autowired
    SmsStatusRepository smsStatusRepository;

    //    private int day = ${crawler.cron.day.kskh};
//    private int day = 2;


    @Scheduled(fixedRateString = "${crawler.cron.delay}")
    public void importData() throws InterruptedException {
        //
        //        sms cskh //
        //
        cskhQuery();
        //
        //        sms cskh //
        //
    }

    public void cskhQuery() throws InterruptedException {
        // get table name from entity annotation
        String table = Cskh.class.getAnnotation(Table.class).name();
//        // get flag key from table name log
        Long flag = smsStatusRepository.getMaxKey(table);
//        // get list customer from key flag
        List<Cskh> cskhs = cskhRepository.findByKey(flag);

//        List<Cskh> cskhs = cskhRepository.findByStatus(0);
        int i = 0;
        for (Cskh cskh : cskhs) {
            if (cskh.getPhone().length() != 10) continue;
            i = i + 1;
            String content = getContentKskh(cskh);
            log.info(cskh.getPhone());
            log.info(content);
            if (i%50==0 &i>0) {
                Thread.sleep(10000);
                log.info(Integer.toString(i));
            }
            try {
                String result = smsService.sendSMS(cskh.getPhone(), content, 3, "HTAUTO");
                smsStatusRepository.save(new SmsStatus(table, cskh.getCode(),cskh.getPhone(), cskh.getId(), result));
                log.info(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info(Integer.toString(i));
    }

    private String getContentKskh(Cskh cskh) {
        return "Cam on Quy khach da mua hang tai HT Auto. Vui long danh gia dich vu tai HT Auto: https://cskh.htauto.vn/HT02/" +
                cskh.getCode() +
                ". CSKH:0888315599. Tran trong cam on!";
    }
//    private String getConectRevenuesMoth(Cskh cskh){
//        "HTAuto tran trong thong bao hang the hoi vien cua Quy khach la: "+
//                $value->level.", so diem thuong: ".$value->coin." diem. Chi tiet CT: https://htauto.com.vn/chinh-sach-khach-hang/";
//    }
}
