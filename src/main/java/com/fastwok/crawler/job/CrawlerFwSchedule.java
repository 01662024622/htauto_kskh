package com.fastwok.crawler.job;

import com.fastwok.crawler.entities.Cskh;
import com.fastwok.crawler.entities.Revenue;
import com.fastwok.crawler.entities.SmsStatus;
import com.fastwok.crawler.model.dto.RevenueDTO;
import com.fastwok.crawler.repository.CskhRepository;
import com.fastwok.crawler.repository.CustomerRepository;
import com.fastwok.crawler.repository.RevenuesRepository;
import com.fastwok.crawler.repository.SmsStatusRepository;
import com.fastwok.crawler.services.isservice.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.io.IOException;
import java.text.DateFormat;
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
    @Autowired
    RevenuesRepository revenuesRepository;
    private static final String SENDER = "HTAUTO";
    private static final int SENDERTYPE = 3;

    //    private int day = ${crawler.cron.day.kskh};
//    private int day = 2;


//    @Scheduled(fixedRateString = "${crawler.cron.delay}")
    public void importData() throws InterruptedException {
        //
        //        sms cskh //
        //
//        cskhQuery();
//        revenueQuery();
        extendedQuery();
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
            if (i % 50 == 0 & i > 0) {
                Thread.sleep(10000);
                log.info(Integer.toString(i));
            }
            try {
                String result = smsService.sendSMS(cskh.getPhone(), content, SENDERTYPE, SENDER);
                smsStatusRepository.save(new SmsStatus(table, cskh.getCode(), cskh.getPhone(), cskh.getId(), result));
                log.info(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info(Integer.toString(i));
    }

    public void revenueQuery() throws InterruptedException {
        // get table name from entity annotation
        String table = Revenue.class.getAnnotation(Table.class).name();

        List<RevenueDTO> revenues = revenuesRepository.getCustomerClose();

        int i = 0;
        for (RevenueDTO revenue : revenues) {
            if (revenue.getPhone().length() != 10) continue;
            i = i + 1;
            String content = getConectRevenuesMoth(revenue);
            log.info(revenue.getPhone());
            log.info(content);
            if (i % 10 == 0 & i > 0) {
                Thread.sleep(20000);
                log.info(Integer.toString(i));
            }
            try {
                String result = smsService.sendSMS(revenue.getPhone(), content, SENDERTYPE, SENDER);
                smsStatusRepository.save(new SmsStatus(table, revenue.getCode(), revenue.getPhone(), revenue.getId(), result));
                log.info(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Thread.sleep(2000000);
        log.info(Integer.toString(i));
    }

    public void extendedQuery() throws InterruptedException {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        // get table name from entity annotation
        String table = Revenue.class.getAnnotation(Table.class).name();

        List<RevenueDTO> revenues = revenuesRepository.getCustomerClose();

        StringBuilder phoneTotal = new StringBuilder();
        for (RevenueDTO revenue : revenues) {
            if (revenue.getPhone().length() != 10) continue;
            phoneTotal.append("\",\"").append(revenue.getPhone());
            log.info(revenue.getPhone());

        }
        log.info(phoneTotal.substring(3).toString());
        String content = getContentExtend();
        try {
            String result = smsService.sendSMS(phoneTotal.toString(), content, SENDERTYPE, SENDER);
            smsStatusRepository.save(new SmsStatus(table, strDate, phoneTotal.toString(), 200L, result));
            log.info(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread.sleep(2000000);
    }

    private String getContentKskh(Cskh cskh) {
        return "Cam on Quy khach da mua hang tai HT Auto. Vui long danh gia dich vu tai HT Auto: https://cskh.htauto.vn/HT02/" +
                cskh.getCode() +
                ". CSKH:0888315599. Tran trong cam on!";
    }

    private String getConectRevenuesMoth(RevenueDTO revenues) {
        return "HTAuto tran trong thong bao tong diem thuong cua Quy khach den ngay 31/11 la:" + (revenues.getCoin() - revenues.getUsed())+
                ". Diem thuong se het han vao 31/1/22. Chi tiet: https://htauto.com.vn/chinh-sach-khach-hang/";
    }

    private String getContentExtend() {
        return "HTAuto tran trong thong bao diem thuong cua quy khach se duoc gia han den ngay 31/1/22. Chi tiet: https://htauto.com.vn/chinh-sach-khach-hang/";
    }
}
