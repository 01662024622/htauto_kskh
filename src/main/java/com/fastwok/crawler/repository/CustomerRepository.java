package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.Cskh;
import com.fastwok.crawler.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByBirthdayStartsWith(String day);

    @Query("select c.id,c.phone,c.code,r.coin,r.level from Cskh c INNER JOIN Revenue r ON c.code=r.code order by c.id")
    List<Cskh> findByKey(@Param("id") Long id);
}
