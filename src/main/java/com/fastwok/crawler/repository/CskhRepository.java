package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.Cskh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CskhRepository extends JpaRepository<Cskh, Long> {

    @Query("select c from Cskh c where c.id > :id order by c.id")
    List<Cskh> findByKey(@Param("id") Long id);

    @Query("select max(c.status) from Cskh c")
    Integer getMaxStauts();

    @Query("select c from Cskh c where c.status = :status order by c.id")
    List<Cskh> findByStatus(@Param("status") Integer id);
}
