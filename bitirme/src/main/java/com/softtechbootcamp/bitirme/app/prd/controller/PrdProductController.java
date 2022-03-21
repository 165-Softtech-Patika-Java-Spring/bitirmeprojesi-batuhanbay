package com.softtechbootcamp.bitirme.app.prd.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.service.PrdProductService;
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

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<PrdProductResponseDto> prdProductResponseDtoList = prdProductService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDtoList));
    }

    @GetMapping("/{productTypeId}")
    public ResponseEntity<?> findAllByProductTypeId(@PathVariable("productTypeId") Long productTypeId){
        List<PrdProductResponseDto> prdProductResponseDtoList =  prdProductService.findAllByProductTypeId(productTypeId);
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDtoList));
    }

    @GetMapping("/between")
    public ResponseEntity<?> findAllPrdProductsBetween(
           @RequestParam("minPrice") BigDecimal minPrice,
           @RequestParam("maxPrice") BigDecimal maxPrice){
       List<PrdProductResponseDto> prdProductResponseDtoList = prdProductService.findAllBetweenMinAndMaxPrice(minPrice,maxPrice);
       return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDtoList));
    }

    @Validated
    @PostMapping
    public  ResponseEntity<?> save(@Valid @RequestBody PrdProductDto prdProductDto){
        PrdProductResponseDto prdProductResponseDto = prdProductService.save(prdProductDto);
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDto));
    }

    @Validated
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody PrdProductDto prdProductDto){
        PrdProductResponseDto prdProductResponseDto = prdProductService.update(id, prdProductDto);
        return ResponseEntity.ok(GeneralResponse.of(prdProductResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        prdProductService.delete(id);
        return ResponseEntity.ok(GeneralResponse.empty());
    }

}
