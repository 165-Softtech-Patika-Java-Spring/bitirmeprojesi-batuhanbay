package com.softtechbootcamp.bitirme.app.prd.dao;

import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PrdProductRepository extends JpaRepository<PrdProduct, Long> {
    boolean existsByNameAndProductTypeId(String name, Long productTypeId);
    boolean existsByProductTypeId(Long prtProductTypeId);
    List<PrdProduct> findAllByProductTypeId(Long productTypeId);
    List<PrdProduct> findAllByLastPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
