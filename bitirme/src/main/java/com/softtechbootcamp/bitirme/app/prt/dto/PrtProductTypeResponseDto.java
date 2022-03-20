package com.softtechbootcamp.bitirme.app.prt.dto;

import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrtProductTypeResponseDto {
    Long id;
    PrtProductTypeName name;
    BigDecimal kdv;
}
