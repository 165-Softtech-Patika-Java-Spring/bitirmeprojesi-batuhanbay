package com.softtechbootcamp.bitirme.app.usr.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import com.softtechbootcamp.bitirme.app.usr.service.UsrUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsrUserController {
    private UsrUserService usrUserService;

    @Operation(tags = "User", description = "This method find all users", summary = "Find all users")
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<UsrUserDto> usrUserDtoList = usrUserService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(usrUserDtoList));
    }

    @Operation(tags = "User", description = "This method find user by given id", summary = "Find user by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "1"), required = true)
            @PathVariable("id") Long id){
        UsrUserDto usrUserDto = usrUserService.findById(id);
        return ResponseEntity.ok(GeneralResponse.of(usrUserDto));
    }

    @Operation(tags = "User", description = "This method find user by given usuernme", summary = "Find user by username")
    @GetMapping("/by/username/{username}")
    public ResponseEntity<?> findByUsername(
            @Parameter(name = "username", in = ParameterIn.PATH, schema = @Schema(type = "string", example  = "softtech"), required = true)
            @PathVariable("username") String username){
        UsrUserDto usrUserDto = usrUserService.findByUsername(username);
        return  ResponseEntity.ok(GeneralResponse.of(usrUserDto));
    }

    @Operation(
            tags = "User", description = "This method save a new user.", summary = "Creates new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UsrUserDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"name\": \"Softtech\",\"surname\": \"Softtech\", \"username\": \"softtech\" ,\"password\": \"password\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @Validated
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsrUserDto usrUserDto){
        UsrUserDto responseUsrUserDto = usrUserService.save(usrUserDto);
        return ResponseEntity.ok(GeneralResponse.of(responseUsrUserDto));
    }

    @Operation(
            tags = "User", description = "This method update a user.", summary = "Update a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UsrUserDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"name\": \"Softtech Test\",\"surname\": \"Softtech Test\", \"username\": \"softtech_test\" ,\"password\": \"password\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @Validated
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestBody @Valid UsrUserDto usrUserDto,
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "1"), required = true)
            @PathVariable("id") Long id){
        UsrUserDto responseUsrUserDto = usrUserService.update(usrUserDto, id);
        return ResponseEntity.ok(GeneralResponse.of(responseUsrUserDto));
    }

    @Operation(tags = "User", description = "This method delete user", summary = "Delete user")
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "1"), required = true)
            @PathVariable("id") Long id){
        usrUserService.delete(id);
        return ResponseEntity.ok(GeneralResponse.empty());
    }
}
