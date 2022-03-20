package com.softtechbootcamp.bitirme.app.prd.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrdProductResponseDto {
    private String name;
    private BigDecimal initialPrice;
    private BigDecimal kdvAmount;
    private BigDecimal lastPrice;
    private Long productTypeId;
}
