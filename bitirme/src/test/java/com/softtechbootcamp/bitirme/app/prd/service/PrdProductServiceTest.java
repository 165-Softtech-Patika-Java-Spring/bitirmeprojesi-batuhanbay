package com.softtechbootcamp.bitirme.app.prd.service;

import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prd.service.entityService.PrdProductEntityService;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
import com.softtechbootcamp.bitirme.app.prt.service.PrtProductTypeService;
import com.softtechbootcamp.bitirme.app.prt.service.entityService.PrtProductTypeEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrdProductServiceTest {

    @Mock
    private PrdProductEntityService prdProductEntityService;

    @Mock
    private PrtProductTypeEntityService prtProductTypeEntityService;


    @InjectMocks
    private PrdProductService prdProductService;

    @Test
    void shouldFindAllWhenPrdProductIsExist() {
        List<PrdProduct> prdProductList = createDummyPrdProductList();

        List<PrdProductResponseDto> expectedResult = createDummyPrdProductResponseDtoList();

        when(prdProductEntityService.findAllWithControl()).thenReturn(prdProductList);

        List<PrdProductResponseDto> result = prdProductService.findAll();

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldFindAllByProductTypeIdWhenPrdProductIdIsExist() {
        Long existPrdProductId = 1L;
        List<PrdProduct> prdProductList = createDummyPrdProductList();

        List<PrdProductResponseDto> expectedResult = createDummyPrdProductResponseDtoList();

        when(prdProductEntityService.findAllByProductTypeIdWithControl(existPrdProductId)).thenReturn(prdProductList);

        List<PrdProductResponseDto> result = prdProductService.findAllByProductTypeId(existPrdProductId);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldFindAllBetweenMinAndMaxPriceWhenPrdProductExistBetweenMinAndMaxPrice() {
        BigDecimal minPrice = BigDecimal.ONE;
        BigDecimal maxPrice = BigDecimal.valueOf(2000);
        List<PrdProduct> prdProductList = createDummyPrdProductList();
        List<PrdProductResponseDto> expectedResult = createDummyPrdProductResponseDtoList();


        when(prdProductEntityService.findAllByInitialPriceBetweenMinPriceAndMaxPriceWithControl(minPrice,maxPrice)).thenReturn(prdProductList);


        List<PrdProductResponseDto> result = prdProductService.findAllBetweenMinAndMaxPrice(minPrice,maxPrice);

        assertEquals(expectedResult, result);
        assertNotNull(result);

    }

    @Test
    void shouldSaveWhenPrdProductDtoIsValid() {
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();
        PrtProductType prtProductType = createDummyPrtProductType();
        PrdProduct prdProduct = createDummyTestPrdProduct();
        PrdProductResponseDto expectedResult = createDummyTestPrdProductResponseDto();

        doNothing().when(prdProductEntityService).checkExistByNameAndProductTypeId(prdProductDto.getName(), prdProductDto.getProductTypeId());
        when(prtProductTypeEntityService.findByIdWithControl(prdProductDto.getProductTypeId())).thenReturn(prtProductType);
        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductResponseDto result = prdProductService.save(prdProductDto);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenIdAndPrdProductDtoAreValid() {
        Long existPrdProductId = 1L;
        PrdProduct prdProduct = createDummyTestPrdProduct();
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();
        PrtProductType prtProductType = createDummyPrtProductType();
        PrdProductResponseDto expectedResult = createDummyTestPrdProductResponseDto();

        when(prdProductEntityService.findByIdWithControl(existPrdProductId)).thenReturn(prdProduct);
        when(prtProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prtProductType);
        when(prdProductEntityService.save(any())).thenReturn(prdProduct);


        PrdProductResponseDto result = prdProductService.update(existPrdProductId, prdProductDto);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenIdAndPrdProductDtoAreValidAndInitialPriceChange() {
        Long existPrdProductId = 1L;
        PrdProduct prdProduct = createDummyTestPrdProduct();
        PrdProductDto prdProductDto = createDummyTestPrdProductDtoInitialPriceChanged();
        PrtProductType prtProductType = createDummyPrtProductType();
        PrdProductResponseDto expectedResult = createDummyTestPrdProductResponseDtoInitialPriceChanged();

        when(prdProductEntityService.findByIdWithControl(existPrdProductId)).thenReturn(prdProduct);
        when(prtProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prtProductType);
        when(prdProductEntityService.save(any())).thenReturn(prdProduct);


        PrdProductResponseDto result = prdProductService.update(existPrdProductId, prdProductDto);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldBatchUpdateWhenPrdProductsHasProductTypeId() {
        Long existProductTypeId = 1L;
        PrtProductType prtProductType = createDummyPrtProductType();
        List<PrdProduct> prdProductList = createDummyPrdProductList();

        when(prtProductTypeEntityService.findByIdWithControl(existProductTypeId)).thenReturn(prtProductType);
        when(prdProductEntityService.findAllByProductTypeIdWithControl(existProductTypeId)).thenReturn(prdProductList);

        prdProductService.batchUpdate(existProductTypeId);

        verify(prdProductEntityService).saveALl(prdProductList);
    }

    @Test
    void shouldDeleteWhenPrdProductIdIsExist() {
        doNothing().when(prdProductEntityService).deleteByIdWithControl(anyLong());
        prdProductService.delete(anyLong());
        verify(prdProductEntityService).deleteByIdWithControl(anyLong());
    }

    private PrdProduct createDummyTestPrdProduct(){
        PrdProduct prdProduct = new PrdProduct();
        prdProduct.setId(1L);
        prdProduct.setInitialPrice(BigDecimal.TEN);
        prdProduct.setKdvAmount(BigDecimal.valueOf(100));
        prdProduct.setLastPrice(BigDecimal.valueOf(110));
        prdProduct.setProductTypeId(1L);
        prdProduct.setName("Test");
        return prdProduct;
    }

    private PrdProductDto createDummyTestPrdProductDto(){
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("Test");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(1L);
        return prdProductDto;
    }

    private PrdProductDto createDummyTestPrdProductDtoInitialPriceChanged(){
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("Test");
        prdProductDto.setInitialPrice(BigDecimal.valueOf(20));
        prdProductDto.setProductTypeId(1L);
        return prdProductDto;
    }

    private PrdProductResponseDto createDummyTestPrdProductResponseDto(){
        PrdProductResponseDto prdProductResponseDto = new PrdProductResponseDto();
        prdProductResponseDto.setName("Test");
        prdProductResponseDto.setInitialPrice(BigDecimal.TEN);
        prdProductResponseDto.setKdvAmount(BigDecimal.valueOf(100));
        prdProductResponseDto.setLastPrice(BigDecimal.valueOf(110));
        prdProductResponseDto.setProductTypeId(1L);
        return prdProductResponseDto;
    }

    private PrdProductResponseDto createDummyTestPrdProductResponseDtoInitialPriceChanged(){
        PrdProductResponseDto prdProductResponseDto = new PrdProductResponseDto();
        prdProductResponseDto.setName("Test");
        prdProductResponseDto.setInitialPrice(BigDecimal.valueOf(20));
        prdProductResponseDto.setKdvAmount(new BigDecimal("2.00"));
        prdProductResponseDto.setLastPrice(new BigDecimal("22.00"));
        prdProductResponseDto.setProductTypeId(1L);
        return prdProductResponseDto;
    }

    private PrtProductType createDummyPrtProductType(){
        PrtProductType prtProductType = new PrtProductType();
        prtProductType.setId(1L);
        prtProductType.setName(PrtProductTypeName.OTHERS);
        prtProductType.setKdv(BigDecimal.TEN);
        return  prtProductType;
    }

    private List<PrdProductResponseDto> createDummyPrdProductResponseDtoList(){
        List<PrdProductResponseDto> prdProductResponseDtoList = new ArrayList<>();
        PrdProductResponseDto prdProductResponseDto = createDummyTestPrdProductResponseDto();
        prdProductResponseDtoList.add(prdProductResponseDto);
        return prdProductResponseDtoList;
    }

    private List<PrdProduct> createDummyPrdProductList(){
        List<PrdProduct> prdProductList = new ArrayList<>();
        PrdProduct prdProduct = createDummyTestPrdProduct();
        prdProductList.add(prdProduct);
        return prdProductList;
    }
}