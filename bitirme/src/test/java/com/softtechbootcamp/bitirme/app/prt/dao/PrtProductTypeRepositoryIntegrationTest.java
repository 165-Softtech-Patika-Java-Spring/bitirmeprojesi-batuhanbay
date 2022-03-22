package com.softtechbootcamp.bitirme.app.prt.dao;

import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.usr.dao.UsrUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PrtProductTypeRepositoryIntegrationTest {

    @Autowired
    private PrtProductTypeRepository prtProductTypeRepository;

    @Test
    void findProductDetail() {
        Long id = 1L;
        BigDecimal kdv = new BigDecimal("1.00");
        PrtProductTypeDetailDto prtProductTypeDetailDto = prtProductTypeRepository.findProductDetail(id);

        assertNotNull(prtProductTypeDetailDto);
        assertEquals(kdv, prtProductTypeDetailDto.getKdv());
    }
}