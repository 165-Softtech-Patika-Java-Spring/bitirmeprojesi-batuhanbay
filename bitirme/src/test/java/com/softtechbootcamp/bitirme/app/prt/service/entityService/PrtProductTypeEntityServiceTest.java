package com.softtechbootcamp.bitirme.app.prt.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.prt.dao.PrtProductTypeRepository;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.usr.dao.UsrUserRepository;
import com.softtechbootcamp.bitirme.app.usr.enums.UsrUserErrorMessage;
import com.softtechbootcamp.bitirme.app.usr.service.entityService.UsrUserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrtProductTypeEntityServiceTest {

    @Mock
    PrtProductTypeRepository prtProductTypeRepository;

    @InjectMocks
    private PrtProductTypeEntityService prtProductTypeEntityService;

    @Test
    void shouldFindProductDetailWhenProductTypeIdIsExist() {

        Long existProductTypeId = 1L;

        PrtProductTypeDetailDto expectedResult = createDummyPrtProductTypeDetailDto();

        when(prtProductTypeRepository.existsById(existProductTypeId)).thenReturn(true);
        when(prtProductTypeRepository.findProductDetail(existProductTypeId)).thenReturn(expectedResult);

        PrtProductTypeDetailDto result = prtProductTypeEntityService.findProductDetail(existProductTypeId);

        assertEquals(expectedResult, result);
        assertNotNull(result);

    }

    @Test
    void shouldNotFindProductDetailWhenProductTypeIdIsNotExist() {

        Long nonExistProductTypeId = 1L;

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prtProductTypeEntityService.findProductDetail(nonExistProductTypeId));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);

    }

    private PrtProductTypeDetailDto createDummyPrtProductTypeDetailDto(){
        PrtProductTypeDetailDto prtProductTypeDetailDto = new PrtProductTypeDetailDto() {
            @Override
            public String getProductTypeName() {
                return "OTHERS";
            }

            @Override
            public BigDecimal getKdv() {
                return BigDecimal.TEN;
            }

            @Override
            public BigDecimal getMinPrice() {
                return BigDecimal.ONE;
            }

            @Override
            public BigDecimal getMaxPrice() {
                return BigDecimal.TEN;
            }

            @Override
            public BigDecimal getAveragePrice() {
                return BigDecimal.valueOf(5.5);
            }

            @Override
            public Long getProductTypeCount() {
                return 1L;
            }


        };
        return prtProductTypeDetailDto;
    }
}