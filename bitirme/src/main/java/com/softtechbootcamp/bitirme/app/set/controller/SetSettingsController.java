package com.softtechbootcamp.bitirme.app.set.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.set.dto.SetSettingsDto;
import com.softtechbootcamp.bitirme.app.set.service.SetSettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class SetSettingsController {
    private SetSettingsService setSettingsService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<SetSettingsDto> setSettingsDtoList = setSettingsService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(setSettingsDtoList));
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody SetSettingsDto setSettingsDto){
        SetSettingsDto savedSetSettingsDto = setSettingsService.updateSettings(setSettingsDto);
        return ResponseEntity.ok(GeneralResponse.of(savedSetSettingsDto));
    }
}
