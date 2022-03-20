package com.softtechbootcamp.bitirme.app.prd.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PrdProductDto {
    @NotNull(message = "Product name parameter can not be null")
    @NotBlank(message = "Product name parameter can not be blank")
    private String name;

    @NotNull(message = "Kdv tax parameter can not be null")
    @DecimalMin(value = "0.1", message = "Product initial price must be greater than 0!")
    private BigDecimal initialPrice;

    @NotNull(message = "Product type parameter can not be null")
    private Long productTypeId;
}
