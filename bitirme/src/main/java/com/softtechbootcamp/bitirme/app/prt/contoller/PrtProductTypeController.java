package com.softtechbootcamp.bitirme.app.prt.contoller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.service.PrtProductTypeService;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
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
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/products/types")
@AllArgsConstructor
public class PrtProductTypeController {
    private PrtProductTypeService prtProductTypeService;

    @Operation(tags = "Product Type", description = "This method find all product types", summary = "Find all product types")
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<PrtProductTypeResponseDto> prtProductTypeResponseDtoList = prtProductTypeService.findAll();
        return ResponseEntity.ok(GeneralResponse.of(prtProductTypeResponseDtoList));
    }

    @Operation(tags = "Product Type", description = "This method find product type detail by given id", summary = "Find product type detail")
    @GetMapping ("/{id}/details")
    ResponseEntity<?> findProductDetail(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "4"), required = true)
            @PathVariable("id") Long id){
        PrtProductTypeDetailDto prtProductTypeDetailDto = prtProductTypeService.findProductDetailByProductTypeId(id);
        return ResponseEntity.ok(GeneralResponse.of(prtProductTypeDetailDto));
    }

    @Operation(
            tags = "Product Type", description = "This method update a product type.", summary = "Update a product type",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UsrUserDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"kdv\": \"25\"}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @Validated
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "1"), required = true)
            @PathVariable("id") Long id,
            @RequestBody @Valid PrtProductTypeDto prtProductTypeDto){
        PrtProductTypeResponseDto prtProductTypeResponseDto = prtProductTypeService.update(id, prtProductTypeDto);
        return ResponseEntity.ok(GeneralResponse.of(prtProductTypeResponseDto));
    }

    @Operation(tags = "Product Type", description = "This method export product type detail pdf file as report by given id", summary = "Export pdf")
    @PostMapping("{id}/detail/pdf")
    public ResponseEntity<?> exportProductTypeDetailReport(
            @Parameter(name = "id", in = ParameterIn.PATH, schema = @Schema(type = "integer", example  = "4"), required = true)
            @PathVariable("id") Long id) throws FileNotFoundException, SQLException {
        prtProductTypeService.exportProductTypeDetailToPdf(id);
        return ResponseEntity.ok(GeneralResponse.empty());
    }

}
