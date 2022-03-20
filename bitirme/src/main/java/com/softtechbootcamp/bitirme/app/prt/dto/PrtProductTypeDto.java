package com.softtechbootcamp.bitirme.app.prt.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PrtProductTypeDto {
    @NotNull(message = "Kdv tax parameter can not be null")
    @DecimalMin(value = "0.0", message = "Kdv must be greater than 0!")
    BigDecimal kdv;
}
