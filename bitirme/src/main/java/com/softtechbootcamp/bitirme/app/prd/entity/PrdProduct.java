package com.softtechbootcamp.bitirme.app.prd.entity;

import com.softtechbootcamp.bitirme.app.gen.entity.BaseEntity;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "prd_product")
public class PrdProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "initial_price", precision = 19, scale = 2, nullable = false)
    private BigDecimal initialPrice;

    @Column(name = "kdv_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal kdvAmount;

    @Column(name = "last_price", precision = 19, scale = 2, nullable = false)
    private BigDecimal lastPrice;

    @Column(name = "id_product_type", nullable = false)
    private Long productTypeId;
}
