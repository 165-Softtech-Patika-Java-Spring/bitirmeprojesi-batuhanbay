package com.softtechbootcamp.bitirme.app.prd.dao;

import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prt.dao.PrtProductTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PrdProductRepositoryIntegrationTest {

    @Autowired
    private PrdProductRepository prdProductRepository;

    @Test
    void existsByNameAndProductTypeId() {
        String name = "Test4";
        Long id = 1L;

        boolean isExistsByNameAndProductTypeId = prdProductRepository.existsByNameAndProductTypeId(name, id);

        assertTrue(isExistsByNameAndProductTypeId);
    }

    @Test
    void existsByProductTypeId() {
        Long prtProductTypeId = 1L;

        boolean isExistsByProductTypeId = prdProductRepository.existsByProductTypeId(prtProductTypeId);

        assertTrue(isExistsByProductTypeId);
    }

    @Test
    void findAllByProductTypeId() {
        Long prtProductTypeId = 1L;

        List<PrdProduct> prdProductList = prdProductRepository.findAllByProductTypeId(prtProductTypeId);

        assertNotNull(prdProductList);
    }

    @Test
    void findAllByLastPriceBetween() {
        BigDecimal minPrice = BigDecimal.valueOf(1);
        BigDecimal maxPrice = BigDecimal.valueOf(900);

        List<PrdProduct> prdProductList = prdProductRepository.findAllByLastPriceBetween(minPrice, maxPrice);

        assertNotNull(prdProductList);
    }
}