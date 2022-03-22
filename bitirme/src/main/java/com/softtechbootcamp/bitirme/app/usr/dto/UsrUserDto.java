package com.softtechbootcamp.bitirme.app.usr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
