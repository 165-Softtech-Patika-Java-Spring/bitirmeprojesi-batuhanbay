package com.softtechbootcamp.bitirme.app.prt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrtProductTypeDto {
    @NotNull(message = "Kdv tax parameter can not be null")
    @DecimalMin(value = "0.0", message = "Kdv must be greater than 0!")
    BigDecimal kdv;
}
