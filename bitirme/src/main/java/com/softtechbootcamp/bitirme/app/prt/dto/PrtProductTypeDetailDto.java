package com.softtechbootcamp.bitirme.app.prt.dto;

import java.math.BigDecimal;

public interface PrtProductTypeDetailDto {
    public String getProductTypeName();
    public BigDecimal getKdv();
    public BigDecimal getMinPrice();
    public BigDecimal getMaxPrice();
    public BigDecimal getAveragePrice();
    public Long getProductTypeCount();

}
