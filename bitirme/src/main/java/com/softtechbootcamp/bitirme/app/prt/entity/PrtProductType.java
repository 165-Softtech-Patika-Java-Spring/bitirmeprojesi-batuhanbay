package com.softtechbootcamp.bitirme.app.prt.entity;

import com.softtechbootcamp.bitirme.app.gen.entity.BaseEntity;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "prt_product_type")
public class PrtProductType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 30, nullable = false)
    private PrtProductTypeName name;


    @Column(name = "kdv", precision = 19, scale = 2, nullable = false)
    private BigDecimal kdv;
}
