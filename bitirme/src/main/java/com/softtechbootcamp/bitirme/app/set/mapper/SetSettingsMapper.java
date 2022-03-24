package com.softtechbootcamp.bitirme.app.set.mapper;

import com.softtechbootcamp.bitirme.app.prt.mapper.PrtProductTypeMapper;
import com.softtechbootcamp.bitirme.app.set.dto.SetSettingsDto;
import com.softtechbootcamp.bitirme.app.set.entity.SetSettings;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SetSettingsMapper {
    SetSettingsMapper INSTANCE = Mappers.getMapper(SetSettingsMapper.class);
    SetSettings convertToSetSettings(SetSettingsDto setSettingsDto);
    SetSettingsDto convertToSetSettingsDto(SetSettings setSettings);
    List<SetSettingsDto> convertToSetSettingsDtoList(List<SetSettings> setSettingsList);
}
