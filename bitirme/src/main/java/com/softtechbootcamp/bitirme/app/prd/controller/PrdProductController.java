package com.softtechbootcamp.bitirme.app.prd.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.service.PrdProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class PrdProductController {
    private PrdProductService prdProductService;

    @Operation(tags = "Product", description = "This method find all products", summary = "Find all products")
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<PrdProductResponseDto> prdProductResponseDtoList = prdProductService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDtoList));
    }

    @Operation(tags = "Product", description = "This method find all products which has same product type", summary = "Find all products which has same product type")
    @GetMapping("/{productTypeId}")
    public ResponseEntity<?> findAllByProductTypeId(
            @Parameter(name = "productTypeId", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "4"), required = true)
            @PathVariable("productTypeId") Long productTypeId){
        List<PrdProductResponseDto> prdProductResponseDtoList =  prdProductService.findAllByProductTypeId(productTypeId);
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDtoList));
    }

    @Operation(tags = "Product", description = "This method find all products which has last price between minimum and maximum price",
            summary = "Find all products which has last price between minimum and maximum price")
    @GetMapping("/between")
    public ResponseEntity<?> findAllPrdProductsBetween(
            @Parameter(name = "minPrice", in = ParameterIn.QUERY, schema = @Schema(type = "number", example  = "1"), required = true)
            @RequestParam("minPrice") BigDecimal minPrice,
            @Parameter(name = "maxPrice", in = ParameterIn.QUERY, schema = @Schema(type = "number", example  = "1000"), required = true)
            @RequestParam("maxPrice") BigDecimal maxPrice){
       List<PrdProductResponseDto> prdProductResponseDtoList = prdProductService.findAllBetweenMinAndMaxPrice(minPrice,maxPrice);
       return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDtoList));
    }

    @Operation(
            tags = "Product", description = "This method save a new product.", summary = "Creates new product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = PrdProductDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"name\": \"Phone\",\"initialPrice\": \"100\",\"productTypeId\": \"4\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @Validated
    @PostMapping
    public  ResponseEntity<?> save(@Valid @RequestBody PrdProductDto prdProductDto){
        PrdProductResponseDto prdProductResponseDto = prdProductService.save(prdProductDto);
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDto));
    }

    @Operation(
            tags = "Product", description = "This method update a product.", summary = "Update a product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = PrdProductDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"name\": \"Phone\",\"initialPrice\": \"600\",\"productTypeId\": \"4\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @Validated
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "1"), required = true)
            @PathVariable("id") Long id,
            @Valid @RequestBody PrdProductDto prdProductDto){
        PrdProductResponseDto prdProductResponseDto = prdProductService.update(id, prdProductDto);
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDto));
    }

    @Operation(tags = "Product", description = "This method delete product", summary = "Delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "1"), required = true)
            @PathVariable("id") Long id){
        prdProductService.delete(id);
        return ResponseEntity.ok(GeneralResponse.empty());
    }

}
