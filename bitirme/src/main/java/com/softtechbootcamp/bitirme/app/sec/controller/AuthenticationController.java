package com.softtechbootcamp.bitirme.app.sec.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.sec.dto.SecLoginRequestDto;
import com.softtechbootcamp.bitirme.app.sec.service.AuthenticationService;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
            tags = "Authentication", description = "This method make user to login", summary = "Login user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = SecLoginRequestDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"username\": \"cadmin\",\"password\": \"password\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecLoginRequestDto secLoginRequestDto){

        String token = authenticationService.login(secLoginRequestDto);

        return ResponseEntity.ok(GeneralResponse.of(token));
    }

    @Operation(
            tags = "Authentication", description = "This method make user to sign up", summary = "Sign up User",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = SecLoginRequestDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"name\": \"Admin\",\"surname\": \"Admin\", \"username\": \"cadmin\" ,\"password\": \"password\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsrUserDto usrUserDto){

        UsrUserDto registeredUsrUserDto = authenticationService.register(usrUserDto);

        return ResponseEntity.ok(GeneralResponse.of(registeredUsrUserDto));
    }
}
