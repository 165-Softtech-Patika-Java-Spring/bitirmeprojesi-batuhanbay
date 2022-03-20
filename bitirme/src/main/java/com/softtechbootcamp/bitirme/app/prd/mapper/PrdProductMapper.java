package com.softtechbootcamp.bitirme.app.prd.mapper;

import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prt.mapper.PrtProductTypeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PrdProductMapper {
    PrdProductMapper INSTANCE = Mappers.getMapper(PrdProductMapper.class);

    PrdProduct convertToPrdProduct(PrdProductDto prdProductDto);
    PrdProductResponseDto convertToPrdProductResponseDto(PrdProduct prdProduct);
    List<PrdProductResponseDto> convertToPrdProductResponseDtoList(List<PrdProduct> prdProductList);

}
