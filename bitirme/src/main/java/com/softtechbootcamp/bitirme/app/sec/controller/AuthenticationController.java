package com.softtechbootcamp.bitirme.app.sec.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.sec.dto.SecLoginRequestDto;
import com.softtechbootcamp.bitirme.app.sec.service.AuthenticationService;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(tags = "Authentication Controller")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecLoginRequestDto secLoginRequestDto){

        String token = authenticationService.login(secLoginRequestDto);

        return ResponseEntity.ok(GeneralResponse.of(token));
    }

    @Operation(tags = "Authentication Controller")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsrUserDto usrUserDto){

        UsrUserDto registeredUsrUserDto = authenticationService.register(usrUserDto);

        return ResponseEntity.ok(GeneralResponse.of(registeredUsrUserDto));
    }
}
