package com.softtechbootcamp.bitirme.app.set.service;

import com.softtechbootcamp.bitirme.app.set.dto.SetSettingsDto;
import com.softtechbootcamp.bitirme.app.set.entity.SetSettings;
import com.softtechbootcamp.bitirme.app.set.mapper.SetSettingsMapper;
import com.softtechbootcamp.bitirme.app.set.service.entityService.SetSettingsEntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SetSettingsService {

    private SetSettingsEntityService setSettingsEntityService;

    public List<SetSettingsDto> findAll() {
        List<SetSettings> settingsList = setSettingsEntityService.findAllWithControl();
        return SetSettingsMapper.INSTANCE.convertToSetSettingsDtoList(settingsList);
    }

    public SetSettingsDto updateSettings(SetSettingsDto setSettingsDto){
        SetSettings setSettings = setSettingsEntityService.findByKeyWithControl(setSettingsDto.getKey());
        setSettings.setValue(setSettingsDto.getValue());
        return SetSettingsMapper.INSTANCE.convertToSetSettingsDto(setSettingsEntityService.save(setSettings));
    }

    public SetSettingsDto findByKey(String key){
        return SetSettingsMapper.INSTANCE.convertToSetSettingsDto(setSettingsEntityService.findByKeyWithControl(key));
    }
}
