package com.softtechbootcamp.bitirme.app.set.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SetSettingsDto {
    @NotNull(message = "Key name parameter can not be null")
    @NotBlank(message = "Key name parameter can not be blank")
    private String key;

    @NotNull(message = "Value name parameter can not be null")
    @NotBlank(message = "Value name parameter can not be blank")
    private String value;
}
