package com.softtechbootcamp.bitirme.app.prt.service;

import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.BusinessExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.prd.service.PrdProductService;
import com.softtechbootcamp.bitirme.app.prd.service.entityService.PrdProductEntityService;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeErrorMessage;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
import com.softtechbootcamp.bitirme.app.prt.service.entityService.PrtProductTypeEntityService;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrtProductTypeServiceTest {

    @Mock
    private PrtProductTypeEntityService prtProductTypeEntityService;
    @Mock
    private PrdProductEntityService prdProductEntityService;
    @Mock
    private PrdProductService prdProductService;

    @InjectMocks
    private PrtProductTypeService prtProductTypeService;

    @Test
    void shouldFindAllWhenPrtProductTypeExist() {
        List<PrtProductType> prtProductTypeList = createDummyPrtProductTypeList();

        List<PrtProductTypeResponseDto> expectedResult = createDummyTestPrtProductTypeResponseDtoList();

        when(prtProductTypeEntityService.findAllWithControl()).thenReturn(prtProductTypeList);

        List<PrtProductTypeResponseDto> result = prtProductTypeService.findAll();

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllWhenPrtProductTypeIsNotExist() {
        BusinessExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITIES_NOT_FOUND);

        when(prtProductTypeEntityService.findAllWithControl()).thenThrow( new EntityNotFoundExceptions(GeneralErrorMessage.ENTITIES_NOT_FOUND));

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prtProductTypeService.findAll());

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldFindProductDetailByProductTypeIdWhenProductTypeIdExist() {
        PrtProductTypeDetailDto expectedResult = createDummyPrtProductTypeDetailDto();

        when(prtProductTypeEntityService.existsByIdWithControl(anyLong())).thenReturn(true);
        when(prtProductTypeEntityService.findProductDetail(anyLong())).thenReturn(expectedResult);

        PrtProductTypeDetailDto result = prtProductTypeService.findProductDetailByProductTypeId(anyLong());

        assertEquals(expectedResult, result);
        assertNotNull(result);

    }

    @Test
    void shouldNotFindProductDetailByProductTypeIdWhenProductTypeIdIsNotExist() {
        Long nonExistPrtProductTypeId = 1L;
        BusinessExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        doThrow(new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND)).when(prtProductTypeEntityService).existsByIdWithControl(anyLong());

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prtProductTypeService.findProductDetailByProductTypeId(nonExistPrtProductTypeId));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);

    }

    @Test
    void shouldUpdateWhenPrtProductTypeDoesNotHaveRelationWithAnyProduct() {

        Long id = 1L;
        PrtProductTypeDto prtProductTypeDto = createDummyPrtProductTypeDto();
        PrtProductType prtProductType = createDummyPrtProductType();
        PrtProductTypeResponseDto expectedResult = createDummyTestPrtProductTypeResponseDto();


        when(prtProductTypeEntityService.findByIdWithControl(id)).thenReturn(prtProductType);
        when(prtProductTypeEntityService.save(prtProductType)).thenReturn(prtProductType);
        when(prdProductEntityService.checkExistByProductYpeId(prtProductType.getId())).thenReturn(false);

        PrtProductTypeResponseDto result = prtProductTypeService.update(id, prtProductTypeDto);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenPrtProductTypeHasRelationWithProduct() {

        Long id = 1L;
        PrtProductTypeDto prtProductTypeDto = createDummyPrtProductTypeDto();
        PrtProductType prtProductType = createDummyPrtProductType();
        PrtProductTypeResponseDto expectedResult = createDummyTestPrtProductTypeResponseDto();


        when(prtProductTypeEntityService.findByIdWithControl(id)).thenReturn(prtProductType);
        when(prtProductTypeEntityService.save(prtProductType)).thenReturn(prtProductType);
        when(prdProductEntityService.checkExistByProductYpeId(prtProductType.getId())).thenReturn(true);
        doNothing().when(prdProductService).batchUpdate(prtProductType.getId());


        PrtProductTypeResponseDto result = prtProductTypeService.update(id, prtProductTypeDto);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateWhenPrtProductTypeHasRelationWithProductAndEncounterError() {

        Long id = 1L;
        PrtProductTypeDto prtProductTypeDto = createDummyPrtProductTypeDto();
        PrtProductType prtProductType = createDummyPrtProductType();
        BusinessExceptions expectedException = new BusinessExceptions(GeneralErrorMessage.INTERNAL_SERVER_ERROR);


        when(prtProductTypeEntityService.findByIdWithControl(id)).thenReturn(prtProductType);
        when(prtProductTypeEntityService.save(prtProductType)).thenReturn(prtProductType);
        when(prdProductEntityService.checkExistByProductYpeId(prtProductType.getId())).thenReturn(true);
        doThrow(new RuntimeException()).when(prdProductService).batchUpdate(prtProductType.getId());

        BusinessExceptions result = assertThrows(BusinessExceptions.class, () -> prtProductTypeService.update(id, prtProductTypeDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);

    }

    @Test
    void shouldNotUpdateWhenPrtProductTypeIdIsNotExist() {

        Long nonExistPrtProductTypeId = 1L;
        PrtProductTypeDto prtProductTypeDto = createDummyPrtProductTypeDto();
        BusinessExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);


        when(prtProductTypeEntityService.findByIdWithControl(nonExistPrtProductTypeId)).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prtProductTypeService.update(nonExistPrtProductTypeId, prtProductTypeDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);

    }

    private PrtProductTypeDto createDummyPrtProductTypeDto(){
        PrtProductTypeDto prtProductTypeDto = new PrtProductTypeDto();
        prtProductTypeDto.setKdv(BigDecimal.TEN);
        return prtProductTypeDto;
    }

    private PrtProductType createDummyPrtProductType(){
        PrtProductType prtProductType = new PrtProductType();
        prtProductType.setId(1L);
        prtProductType.setName(PrtProductTypeName.OTHERS);
        prtProductType.setKdv(BigDecimal.TEN);
        return  prtProductType;
    }

    private List<PrtProductType> createDummyPrtProductTypeList(){
        List<PrtProductType> prtProductTypeList = new ArrayList<>();
        PrtProductType prtProductType = createDummyPrtProductType();
        prtProductTypeList.add(prtProductType);
        return  prtProductTypeList;
    }

    private PrtProductTypeResponseDto createDummyTestPrtProductTypeResponseDto(){
        PrtProductTypeResponseDto prtProductTypeResponseDto = new PrtProductTypeResponseDto();
        prtProductTypeResponseDto.setId(1L);
        prtProductTypeResponseDto.setName(PrtProductTypeName.OTHERS);
        prtProductTypeResponseDto.setKdv(BigDecimal.TEN);
        return  prtProductTypeResponseDto;
    }

    private List<PrtProductTypeResponseDto> createDummyTestPrtProductTypeResponseDtoList(){
        List<PrtProductTypeResponseDto> prtProductTypeResponseDtoList = new ArrayList<>();
        PrtProductTypeResponseDto prtProductTypeResponseDto = createDummyTestPrtProductTypeResponseDto();
        prtProductTypeResponseDtoList.add(prtProductTypeResponseDto);
        return prtProductTypeResponseDtoList;
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