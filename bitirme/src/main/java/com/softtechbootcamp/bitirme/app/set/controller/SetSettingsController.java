package com.softtechbootcamp.bitirme.app.set.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.set.dto.SetSettingsDto;
import com.softtechbootcamp.bitirme.app.set.service.SetSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class SetSettingsController {
    private SetSettingsService setSettingsService;

    @Operation(tags = "Settings", description = "This method find all settings", summary = "Find all settings")
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<SetSettingsDto> setSettingsDtoList = setSettingsService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(setSettingsDtoList));
    }

    @Operation(
            tags = "Settings", description = "This method update a setting.", summary = "Update a setting",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = SetSettingsDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"key\": \"savedDirectoryPath\",\"value\": \"D:\\\\jasper_reports_pdf\\\\\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @Validated
    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody SetSettingsDto setSettingsDto){
        SetSettingsDto savedSetSettingsDto = setSettingsService.updateSettings(setSettingsDto);
        return ResponseEntity.ok(GeneralResponse.of(savedSetSettingsDto));
    }
}
