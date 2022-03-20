package com.softtechbootcamp.bitirme.app.usr.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsrUserDto {
    @NotNull(message = "Username parameter can not be null")
    @NotBlank(message = "Username parameter can not be blank")
    private String username;

    @NotNull(message = "Password parameter can not be null")
    @NotBlank(message = "Password parameter can not be blank")
    private String password;

    private String name;

    private String surname;
}
