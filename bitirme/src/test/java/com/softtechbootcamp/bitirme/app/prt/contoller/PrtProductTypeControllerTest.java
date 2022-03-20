package com.softtechbootcamp.bitirme.app.prt.contoller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrtProductTypeControllerTest {

    @Mock
    private PrtProductTypeService prtProductTypeService;

    @InjectMocks
    private PrtProductTypeController prtProductTypeController;

    @Test
    void shouldFindAllWhenPrtProductTypeExist() {
        List<PrtProductTypeResponseDto> dummyPrtProductTypeResponseDtoList = createDummyTestPrtProductTypeResponseDtoList();

        when(prtProductTypeService.findAll()).thenReturn(dummyPrtProductTypeResponseDtoList);

        ResponseEntity<GeneralResponse<List<PrtProductTypeResponseDto>>> result = (ResponseEntity<GeneralResponse<List<PrtProductTypeResponseDto>>>) prtProductTypeController.findAll();

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyPrtProductTypeResponseDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldFindProductDetailWhenProductHasThatProductType() {
        PrtProductTypeDetailDto dummyPrtProductTypeDetailDto = createDummyPrtProductTypeDetailDto();

        when(prtProductTypeService.findProductDetailByProductTypeId(anyLong())).thenReturn(dummyPrtProductTypeDetailDto);

        ResponseEntity<GeneralResponse<PrtProductTypeDetailDto>> result = (ResponseEntity<GeneralResponse<PrtProductTypeDetailDto>>) prtProductTypeController.findProductDetail(anyLong());

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyPrtProductTypeDetailDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenPrtProductTypeDtoAndIdIsCorrect() {
        PrtProductTypeResponseDto dummyPrtProductTypeResponseDto = createDummyTestPrtProductTypeResponseDto();
        PrtProductTypeDto dummyPrtProductTypeDto = createDummyPrtProductTypeDto();

        when(prtProductTypeService.update(1L,dummyPrtProductTypeDto)).thenReturn(dummyPrtProductTypeResponseDto);

        ResponseEntity<GeneralResponse<PrtProductTypeResponseDto>> result = (ResponseEntity<GeneralResponse<PrtProductTypeResponseDto>>) prtProductTypeController.update(1L, dummyPrtProductTypeDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyPrtProductTypeResponseDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private PrtProductTypeDto createDummyPrtProductTypeDto(){
        PrtProductTypeDto prtProductTypeDto = mock(PrtProductTypeDto.class);
        prtProductTypeDto.setKdv(BigDecimal.TEN);
        return  prtProductTypeDto;
    }

    private PrtProductTypeResponseDto createDummyTestPrtProductTypeResponseDto(){
        PrtProductTypeResponseDto prtProductTypeResponseDto = mock(PrtProductTypeResponseDto.class);
        prtProductTypeResponseDto.setId(1L);
        prtProductTypeResponseDto.setName(PrtProductTypeName.OTHERS);
        prtProductTypeResponseDto.setKdv(BigDecimal.TEN);
        return  prtProductTypeResponseDto;
    }

    private List<PrtProductTypeResponseDto> createDummyTestPrtProductTypeResponseDtoList(){
        List<PrtProductTypeResponseDto>  prtProductTypeResponseDtoList = new ArrayList<>();
        PrtProductTypeResponseDto dummyTestPrtProductTypeResponseDto = createDummyTestPrtProductTypeResponseDto();
        prtProductTypeResponseDtoList.add(dummyTestPrtProductTypeResponseDto);
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