package com.softtechbootcamp.bitirme.app.prd.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.gen.service.BaseEntityService;
import com.softtechbootcamp.bitirme.app.prd.dao.PrdProductRepository;
import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prd.enums.PrdProductErrorMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PrdProductEntityService extends BaseEntityService<PrdProduct, PrdProductRepository> {
    public PrdProductEntityService(PrdProductRepository eRepository) {
        super(eRepository);
    }

    public void saveALl(List<PrdProduct> prdProductList){
        getRepository().saveAll(prdProductList);
    }

    public List<PrdProduct> findAllByProductTypeIdWithControl(Long productTypeId){

        List<PrdProduct> prdProductList = getRepository().findAllByProductTypeId(productTypeId);

        if (prdProductList.isEmpty()){
            throw new EntityNotFoundExceptions(PrdProductErrorMessage.NOT_FOUND_PRODUCT_LIST_WITH_PRODUCT_TYPE);
        }

        return  prdProductList;
    }

    public List<PrdProduct> findAllByInitialPriceBetweenMinPriceAndMaxPriceWithControl(BigDecimal minPrice, BigDecimal maxPrice){
        List<PrdProduct> prdProductList = getRepository().findAllByLastPriceBetween(minPrice, maxPrice);
        if (prdProductList.isEmpty()){
            throw new EntityNotFoundExceptions(PrdProductErrorMessage.NOT_FOUND_PRODUCT_LIST_BETWEEN_MIN_AND_MAX_PRICE);
        }
        return prdProductList;
    }

    public void checkExistByNameAndProductTypeId(String name, Long prtProductTypeId){
        if(getRepository().existsByNameAndProductTypeId(name, prtProductTypeId)){
            throw new DuplicateEntityExceptions(PrdProductErrorMessage.HAS_DUPLICATE_PRODUCT_NAME_IN_PRODUCT_TYPE);
        }
    }

    public boolean checkExistByProductYpeId(Long productTypeId){
        boolean isExist = getRepository().existsByProductTypeId(productTypeId);
        return isExist;
    }

    public void checkMinPriceAndMaxPriceIsValid(BigDecimal minPrice, BigDecimal maxPrice){
        if (minPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MIN_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
        if (maxPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
        if (maxPrice.compareTo(minPrice) <= 0){
            throw new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_MIN_PRICE);
        }
    }

}
