package com.softtechbootcamp.bitirme.app.prt.contoller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.service.PrtProductTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products/types")
@AllArgsConstructor
public class PrtProductTypeController {
    private PrtProductTypeService prtProductTypeService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<PrtProductTypeResponseDto> prtProductTypeResponseDtoList = prtProductTypeService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(prtProductTypeResponseDtoList));
    }

    @GetMapping ("/{id}/details")
    ResponseEntity<?> findProductDetail(@PathVariable("id") Long id){
        PrtProductTypeDetailDto prtProductTypeDetailDto = prtProductTypeService.findProductDetailByProductTypeId(id);
        return ResponseEntity.ok(GeneralResponse.of(prtProductTypeDetailDto));
    }

    @Validated
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid PrtProductTypeDto prtProductTypeDto){
        PrtProductTypeResponseDto prtProductTypeResponseDto = prtProductTypeService.update(id, prtProductTypeDto);
        return ResponseEntity.ok(GeneralResponse.of(prtProductTypeResponseDto));
    }
}
