package com.softtechbootcamp.bitirme.app.prt.service;

import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.BusinessExceptions;
import com.softtechbootcamp.bitirme.app.prd.service.PrdProductService;
import com.softtechbootcamp.bitirme.app.prd.service.entityService.PrdProductEntityService;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeResponseDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.mapper.PrtProductTypeMapper;
import com.softtechbootcamp.bitirme.app.prt.service.entityService.PrtProductTypeEntityService;
import com.softtechbootcamp.bitirme.app.rpt.dto.ExportReportDto;
import com.softtechbootcamp.bitirme.app.rpt.enums.ExportReportErrorMessage;
import com.softtechbootcamp.bitirme.app.rpt.service.ExportReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor
public class PrtProductTypeService {

    private PrtProductTypeEntityService prtProductTypeEntityService;
    private PrdProductEntityService prdProductEntityService;
    private PrdProductService prdProductService;
    private ExportReportService exportReportService;

    public List<PrtProductTypeResponseDto> findAll(){
        List<PrtProductType> prtProductTypeList = prtProductTypeEntityService.findAllWithControl();
        return PrtProductTypeMapper.INSTANCE.convertToPrtProductTypeResponseDtoList(prtProductTypeList);
    }

    public PrtProductTypeDetailDto findProductDetailByProductTypeId(Long id){
        prtProductTypeEntityService.existsByIdWithControl(id);
        return prtProductTypeEntityService.findProductDetail(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PrtProductTypeResponseDto update(Long id, PrtProductTypeDto prtProductTypeDto ){
        PrtProductType prtProductType = prtProductTypeEntityService.findByIdWithControl(id);
        prtProductType.setKdv(prtProductTypeDto.getKdv());
            try {
                PrtProductType savedProductType = prtProductTypeEntityService.save(prtProductType);
                if (prdProductEntityService.checkExistByProductYpeId(prtProductType.getId())) {
                    prdProductService.batchUpdate(prtProductType.getId());
                }
                return PrtProductTypeMapper.INSTANCE.convertToPrtProductTypeResponseDto(savedProductType);
            } catch (Exception ex){
                throw new BusinessExceptions(GeneralErrorMessage.INTERNAL_SERVER_ERROR);
            }
    }

    public void exportProductTypeDetailToPdf(Long id) throws FileNotFoundException, SQLException {
        PrtProductType prtProductType = prtProductTypeEntityService.findByIdWithControl(id);
        ExportReportDto exportReportDto = ExportReportDto.builder()
                .id(prtProductType.getId())
                .name(prtProductType.getName().toString())
                .build();

            exportReportService.generateProductTypeDetailReport(exportReportDto);

    }


}
