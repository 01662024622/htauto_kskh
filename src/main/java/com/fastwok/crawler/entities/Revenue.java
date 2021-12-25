package com.fastwok.crawler.entities;

import com.fastwok.crawler.model.dto.RevenueDTO;
import lombok.Data;

import javax.persistence.*;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "RevenueDTO",
                        classes = @ConstructorResult(
                                targetClass = RevenueDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "code", type = String.class),
                                        @ColumnResult(name = "used", type = Integer.class),
                                        @ColumnResult(name = "coin", type = Integer.class),
                                        @ColumnResult(name = "level", type = String.class),
                                        @ColumnResult(name = "phone", type = String.class)
                                }
                        )
                ),
        }
)
@NamedNativeQuery(
        name = "getCustomerClose",
        resultSetMapping = "RevenueDTO",
        query = "select r.id,r.code,r.used,r.coin,r.level,c.phone from ht50_information_customer_surveys c " +
                "INNER JOIN ht50_revenues r ON c.code=r.code order by r.id"
)
@Data
@Entity
@Table(name = "ht50_revenues")
public class Revenue {
    @Id
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "used")
    private Integer used;
    @Column(name = "coin")
    private Integer coin;
    @Column(name = "level")
    private String level;
}
