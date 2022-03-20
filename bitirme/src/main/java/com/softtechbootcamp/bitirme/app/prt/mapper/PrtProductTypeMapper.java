package com.softtechbootcamp.bitirme.app.prt.mapper;

import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PrtProductTypeMapper {
    PrtProductTypeMapper INSTANCE = Mappers.getMapper(PrtProductTypeMapper.class);

    PrtProductType convertToPrtProductType(PrtProductTypeDto prtProductTypeDto);
    PrtProductType convertToPrtProductTypeFromPrtProductTypeResponseDto(PrtProductTypeResponseDto prtProductTypeResponseDto);
    PrtProductTypeResponseDto convertToPrtProductTypeResponseDto(PrtProductType prtProductType);
    List<PrtProductTypeResponseDto> convertToPrtProductTypeResponseDtoList(List<PrtProductType> prtProductTypeList);
}
