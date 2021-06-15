package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.Customer;
import com.fastwok.crawler.entities.SmsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsStatusRepository extends JpaRepository<SmsStatus, Long> {
    @Query("select max(c.flag) from SmsStatus c where c.table= :table")
    Long getMaxKey(String table);
}
