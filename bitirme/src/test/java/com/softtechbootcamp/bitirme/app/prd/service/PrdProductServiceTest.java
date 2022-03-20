package com.softtechbootcamp.bitirme.app.prd.service;

import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.BusinessExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prd.enums.PrdProductErrorMessage;
import com.softtechbootcamp.bitirme.app.prd.service.entityService.PrdProductEntityService;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeErrorMessage;
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
    void shouldNotFindAllWhenPrdProductIsNotExist() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITIES_NOT_FOUND);

        when(prdProductEntityService.findAllWithControl()).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductService.findAll());

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
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
    void shouldNotFindAllByProductTypeIdWhenPrdProductIdIsNotExist() {
        Long nonExistProductTypeId = 1L;
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(prtProductTypeEntityService.existsByIdWithControl(nonExistProductTypeId)).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductService.findAllByProductTypeId(nonExistProductTypeId));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
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
    void shouldNotFindAllBetweenMinAndMaxPriceWhenPrdProductIsNotExistBetweenMinAndMaxPrice() {
        BigDecimal minPrice = BigDecimal.ONE;
        BigDecimal maxPrice = BigDecimal.valueOf(2000);

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(PrdProductErrorMessage.NOT_FOUND_PRODUCT_LIST_BETWEEN_MIN_AND_MAX_PRICE);

        when(prdProductEntityService.findAllByInitialPriceBetweenMinPriceAndMaxPriceWithControl(minPrice,maxPrice)).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductService.findAllBetweenMinAndMaxPrice(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllBetweenMinAndMaxPriceWhenMinPriceIsLessThanZero() {
        BigDecimal minPrice = BigDecimal.valueOf(-1);
        BigDecimal maxPrice = BigDecimal.valueOf(2000);

        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MIN_PRICE_MUST_BE_GREATER_THAN_ZERO);

        doThrow(new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MIN_PRICE_MUST_BE_GREATER_THAN_ZERO)).when(prdProductEntityService).checkMinPriceAndMaxPriceIsValid(minPrice,maxPrice);
        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductService.findAllBetweenMinAndMaxPrice(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllBetweenMinAndMaxPriceWhenMaxPriceIsLessThanZero() {
        BigDecimal minPrice = BigDecimal.valueOf(1);
        BigDecimal maxPrice = BigDecimal.valueOf(-2000);

        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_ZERO);

        doThrow(new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_ZERO)).when(prdProductEntityService).checkMinPriceAndMaxPriceIsValid(minPrice,maxPrice);
        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductService.findAllBetweenMinAndMaxPrice(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllBetweenMinAndMaxPriceWhenMaxPriceIsLessThanMinPrice() {
        BigDecimal minPrice = BigDecimal.valueOf(2000);
        BigDecimal maxPrice = BigDecimal.valueOf(1000);

        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_MIN_PRICE);

        doThrow(new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_MIN_PRICE)).when(prdProductEntityService).checkMinPriceAndMaxPriceIsValid(minPrice,maxPrice);
        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductService.findAllBetweenMinAndMaxPrice(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
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
    void shouldNotSaveWhenPrdProductDtoNameIsExistWithSameProductType() {
        String existProductName = "Test";
        Long existProductTypeId = 1L;
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();
        DuplicateEntityExceptions expectedException = new DuplicateEntityExceptions(PrdProductErrorMessage.HAS_DUPLICATE_PRODUCT_NAME_IN_PRODUCT_TYPE);

        doThrow(new DuplicateEntityExceptions(PrdProductErrorMessage.HAS_DUPLICATE_PRODUCT_NAME_IN_PRODUCT_TYPE)).when(prdProductEntityService).checkExistByNameAndProductTypeId(existProductName,existProductTypeId);

        DuplicateEntityExceptions result = assertThrows(DuplicateEntityExceptions.class, () -> prdProductService.save(prdProductDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveWhenPrtProductTypeIdIsNotExist() {
        Long nonExistProductTypeId = 1L;
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(prtProductTypeEntityService.findByIdWithControl(nonExistProductTypeId)).thenThrow(new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND));
        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductService.save(prdProductDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveWhenPrdProductInitialPriceIsLessThanZero() {
        BigDecimal initialPrice = BigDecimal.valueOf(-5000);
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();
        prdProductDto.setInitialPrice(initialPrice);
        PrtProductType prtProductType = createDummyPrtProductType();
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_INITIAL_PRICE_MUST_BE_GREATER_THAN_ZERO);

        when(prtProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prtProductType);
        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductService.save(prdProductDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveWhenPrtProductTypeKdvIsLessThanZero() {
        BigDecimal kdv = BigDecimal.valueOf(-5);
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();
        PrtProductType prtProductType = createDummyPrtProductType();
        prtProductType.setKdv(kdv);
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrtProductTypeErrorMessage.PRODUCT_TYPE_KDV_CAN_NOT_LESS_THAN_ZERO);

        when(prtProductTypeEntityService.findByIdWithControl(anyLong())).thenReturn(prtProductType);
        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductService.save(prdProductDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
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
    void shouldNotUpdateWhenPrdProductIdIsNotExist() {
        Long nonExistPrdProductId = 1L;
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(prdProductEntityService.findByIdWithControl(nonExistPrdProductId)).thenThrow(new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND));
        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductService.update(nonExistPrdProductId,prdProductDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateWhenPrtProductTypeIdIsNotExist() {
        Long existPrdProductId = 1L;
        Long nonExistPrdProductTypeId = 1L;
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(prtProductTypeEntityService.findByIdWithControl(nonExistPrdProductTypeId)).thenThrow(new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND));
        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductService.update(existPrdProductId,prdProductDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
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

    @Test
    void shouldNotDeleteWhenPrdProductIdIsExist() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);
        doThrow(new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND)).when(prdProductEntityService).deleteByIdWithControl(anyLong());

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductEntityService.deleteByIdWithControl(anyLong()));

        verify(prdProductEntityService).deleteByIdWithControl(anyLong());
        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
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