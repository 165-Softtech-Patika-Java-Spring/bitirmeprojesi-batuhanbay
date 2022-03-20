package com.softtechbootcamp.bitirme.app.prt.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.service.BaseEntityService;
import com.softtechbootcamp.bitirme.app.prt.dao.PrtProductTypeRepository;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import org.springframework.stereotype.Service;

@Service
public class PrtProductTypeEntityService  extends BaseEntityService<PrtProductType, PrtProductTypeRepository> {
    public PrtProductTypeEntityService(PrtProductTypeRepository eRepository) {
        super(eRepository);
    }

    public PrtProductTypeDetailDto findProductDetail(Long id){
        existsByIdWithControl(id);
        return getRepository().findProductDetail(id);
    }
}
