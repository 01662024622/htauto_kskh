package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.Revenue;
import com.fastwok.crawler.model.dto.RevenueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenuesRepository  extends JpaRepository<Revenue, Long> {
    @Query(nativeQuery = true, name = "getCustomerClose")
    List<RevenueDTO> getCustomerClose();

}
