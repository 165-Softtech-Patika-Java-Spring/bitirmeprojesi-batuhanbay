package com.softtechbootcamp.bitirme.app.prd.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.prd.dao.PrdProductRepository;
import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prd.enums.PrdProductErrorMessage;
import com.softtechbootcamp.bitirme.app.prt.dao.PrtProductTypeRepository;
import com.softtechbootcamp.bitirme.app.prt.service.entityService.PrtProductTypeEntityService;
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
class PrdProductEntityServiceTest {

    @Mock
    PrdProductRepository prdProductRepository;

    @InjectMocks
    private PrdProductEntityService prdProductEntityService;

    @Test
    void shouldSaveALlWhenPrdProductListIsExist() {
        List<PrdProduct> prdProductList = createDummyTestPrdProductList();

        doReturn(prdProductList).when(prdProductRepository).saveAll(prdProductList);

        prdProductEntityService.saveALl(prdProductList);

        verify(prdProductRepository).saveAll(prdProductList);
    }

    @Test
    void shouldFindAllByProductTypeIdWithControlWhenPrtProductTypeIdIsExist() {
        Long existPrtProductTypeId = 1L;
        List<PrdProduct> expectedResult = createDummyTestPrdProductList();

        when(prdProductRepository.findAllByProductTypeId(existPrtProductTypeId)).thenReturn(expectedResult);

        List<PrdProduct> result = prdProductEntityService.findAllByProductTypeIdWithControl(existPrtProductTypeId);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllByProductTypeIdWithControlWhenPrdProductIsNotExistWithThatPrtProductTypeId() {
        Long prtProductTypeId = 1L;

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(PrdProductErrorMessage.NOT_FOUND_PRODUCT_LIST_WITH_PRODUCT_TYPE);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductEntityService.findAllByProductTypeIdWithControl(prtProductTypeId));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldFindAllByInitialPriceBetweenMinPriceAndMaxPriceWithControlWhenPrdProductExistBetweenMinAndMaxPrice() {
        BigDecimal minPrice = BigDecimal.valueOf(1);
        BigDecimal maxPrice = BigDecimal.valueOf(1000);
        List<PrdProduct> expectedResult = createDummyTestPrdProductList();

        when(prdProductRepository.findAllByInitialPriceBetween(minPrice, maxPrice)).thenReturn(expectedResult);

        List<PrdProduct> result = prdProductEntityService.findAllByInitialPriceBetweenMinPriceAndMaxPriceWithControl(minPrice, maxPrice);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllByInitialPriceBetweenMinPriceAndMaxPriceWithControlWhenPrdProductIsNotExistBetweenMinAndMaxPrice() {
        BigDecimal minPrice = BigDecimal.valueOf(1);
        BigDecimal maxPrice = BigDecimal.valueOf(1000);
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(PrdProductErrorMessage.NOT_FOUND_PRODUCT_LIST_BETWEEN_MIN_AND_MAX_PRICE);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> prdProductEntityService.findAllByInitialPriceBetweenMinPriceAndMaxPriceWithControl(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldCheckExistByNameAndProductTypeIdWhenPrdProductNameIsNotExistWithThatProductTypeId() {
        String name = "Test";
        Long existPrtProductTypeId = 1L;

        when(prdProductRepository.existsByNameAndProductTypeId(name, existPrtProductTypeId)).thenReturn(false);

        prdProductEntityService.checkExistByNameAndProductTypeId(name,existPrtProductTypeId);

        verify(prdProductRepository).existsByNameAndProductTypeId(name, existPrtProductTypeId);
    }

    @Test
    void shouldNotCheckExistByNameAndProductTypeIdWhenPrdProductNameIsNotExistWithThatProductTypeId() {
        String name = "Test";
        Long existPrtProductTypeId = 1L;
        DuplicateEntityExceptions expectedException = new DuplicateEntityExceptions(PrdProductErrorMessage.HAS_DUPLICATE_PRODUCT_NAME_IN_PRODUCT_TYPE);

        when(prdProductRepository.existsByNameAndProductTypeId(name, existPrtProductTypeId)).thenReturn(true);
        DuplicateEntityExceptions result = assertThrows(DuplicateEntityExceptions.class, () -> prdProductEntityService.checkExistByNameAndProductTypeId(name, existPrtProductTypeId));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldCheckExistByProductYpeIdWhenProductTypeIdIsExist() {
        Long existPrtProductTypeId = 1L;
        boolean expectedResult = true;

        when(prdProductRepository.existsByProductTypeId(existPrtProductTypeId)).thenReturn(true);

        boolean result = prdProductEntityService.checkExistByProductYpeId(existPrtProductTypeId);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotCheckMinPriceAndMaxPriceIsValidWhenMinPriceIsLessThanZero() {
        BigDecimal minPrice = BigDecimal.valueOf(-1);
        BigDecimal maxPrice = BigDecimal.valueOf(1000);
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MIN_PRICE_MUST_BE_GREATER_THAN_ZERO);

        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductEntityService.checkMinPriceAndMaxPriceIsValid(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotCheckMinPriceAndMaxPriceIsValidWhenMaxPriceIsLessThanZero() {
        BigDecimal minPrice = BigDecimal.valueOf(1);
        BigDecimal maxPrice = BigDecimal.valueOf(-1000);
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_ZERO);

        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductEntityService.checkMinPriceAndMaxPriceIsValid(minPrice, maxPrice));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotCheckMinPriceAndMaxPriceIsValidWhenMaxPriceIsLessThanMinPrice() {
        BigDecimal minPrice = BigDecimal.valueOf(1000);
        BigDecimal maxPrice = BigDecimal.valueOf(500);
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_MIN_PRICE);

        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> prdProductEntityService.checkMinPriceAndMaxPriceIsValid(minPrice, maxPrice));

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

    private List<PrdProduct> createDummyTestPrdProductList(){
        List<PrdProduct> prdProductList = new ArrayList<>();
        PrdProduct prdProduct = createDummyTestPrdProduct();
        prdProductList.add(prdProduct);
        return prdProductList;
    }
}