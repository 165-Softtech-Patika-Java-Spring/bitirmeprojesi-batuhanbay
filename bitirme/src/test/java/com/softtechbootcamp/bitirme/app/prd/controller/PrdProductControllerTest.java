package com.softtechbootcamp.bitirme.app.prd.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.service.PrdProductService;
import com.softtechbootcamp.bitirme.app.prt.contoller.PrtProductTypeController;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.service.PrtProductTypeService;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrdProductControllerTest {

    @Mock
    private PrdProductService prdProductService;

    @InjectMocks
    private PrdProductController prdProductController;

    @Test
    void shouldFindAllWhenPrdProductExist() {
        List<PrdProductResponseDto> prdProductResponseDtoList = createDummyTestPrdProductResponseDtoList();

        when(prdProductService.findAll()).thenReturn(prdProductResponseDtoList);

        ResponseEntity<GeneralResponse<List<PrdProductResponseDto>>> result = (ResponseEntity<GeneralResponse<List<PrdProductResponseDto>>>) prdProductController.findAll();

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),prdProductResponseDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldFindAllByProductTypeIdWhenPrdProductExistWithThatProductTypeId() {
        List<PrdProductResponseDto> prdProductResponseDtoList = createDummyTestPrdProductResponseDtoList();

        when(prdProductService.findAllByProductTypeId(anyLong())).thenReturn(prdProductResponseDtoList);

        ResponseEntity<GeneralResponse<List<PrdProductResponseDto>>> result = (ResponseEntity<GeneralResponse<List<PrdProductResponseDto>>>) prdProductController.findAllByProductTypeId(anyLong());

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),prdProductResponseDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldFindAllPrdProductsBetweenWhenPrdProductExistBetweenMinAndMaxPrice() {
        BigDecimal minPrice = BigDecimal.ONE;
        BigDecimal maxPrice = BigDecimal.valueOf(2000);

        List<PrdProductResponseDto> prdProductResponseDtoList = createDummyTestPrdProductResponseDtoList();

        when(prdProductService.findAllBetweenMinAndMaxPrice(minPrice, maxPrice)).thenReturn(prdProductResponseDtoList);

        ResponseEntity<GeneralResponse<List<PrdProductResponseDto>>> result = (ResponseEntity<GeneralResponse<List<PrdProductResponseDto>>>) prdProductController.findAllPrdProductsBetween(minPrice,maxPrice);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),prdProductResponseDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldSaveWhenPrdProductIsValid() {
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();

        PrdProductResponseDto expectedResult = createDummyTestPrdProductResponseDto();

        when(prdProductService.save(prdProductDto)).thenReturn(expectedResult);

        ResponseEntity<GeneralResponse<PrdProductResponseDto>> result = (ResponseEntity<GeneralResponse<PrdProductResponseDto>>) prdProductController.save(prdProductDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),expectedResult);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenPrdProductIsValid() {
        Long existPrdProductId = 1L;
        PrdProductDto prdProductDto = createDummyTestPrdProductDto();

        PrdProductResponseDto expectedResult = createDummyTestPrdProductResponseDto();

        when(prdProductService.update(existPrdProductId,prdProductDto)).thenReturn(expectedResult);

        ResponseEntity<GeneralResponse<PrdProductResponseDto>> result = (ResponseEntity<GeneralResponse<PrdProductResponseDto>>) prdProductController.update(existPrdProductId,prdProductDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),expectedResult);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldDeleteWhenPrdProductIdIsExist() {
        Long existPrdProductId = 1L;

        doNothing().when(prdProductService).delete(existPrdProductId);

        ResponseEntity<GeneralResponse<?>> result = (ResponseEntity<GeneralResponse<?>>) prdProductController.delete(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);

    }

    private PrdProductDto createDummyTestPrdProductDto(){
        PrdProductDto prdProductDto = mock(PrdProductDto.class);
        prdProductDto.setName("Test");
        prdProductDto.setProductTypeId(1L);
        prdProductDto.setInitialPrice(BigDecimal.valueOf(100));
        return prdProductDto;
    }

    private PrdProductResponseDto createDummyTestPrdProductResponseDto(){
        PrdProductResponseDto prdProductResponseDto = mock(PrdProductResponseDto.class);;
        prdProductResponseDto.setName("Test");
        prdProductResponseDto.setInitialPrice(BigDecimal.valueOf(100));
        prdProductResponseDto.setKdvAmount(BigDecimal.valueOf(50));
        prdProductResponseDto.setLastPrice(BigDecimal.valueOf(150));
        prdProductResponseDto.setProductTypeId(1L);
        return prdProductResponseDto;
    }

    private List<PrdProductResponseDto> createDummyTestPrdProductResponseDtoList(){
        List<PrdProductResponseDto> prdProductResponseDtoList = new ArrayList<>();
        PrdProductResponseDto prdProductResponseDto = createDummyTestPrdProductResponseDto();
        prdProductResponseDtoList.add(prdProductResponseDto);
        return prdProductResponseDtoList;
    }
}