package com.softtechbootcamp.bitirme.app.prd.service;

import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductResponseDto;
import com.softtechbootcamp.bitirme.app.prd.entity.PrdProduct;
import com.softtechbootcamp.bitirme.app.prd.enums.PrdProductErrorMessage;
import com.softtechbootcamp.bitirme.app.prd.mapper.PrdProductMapper;
import com.softtechbootcamp.bitirme.app.prd.service.entityService.PrdProductEntityService;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeErrorMessage;
import com.softtechbootcamp.bitirme.app.prt.service.entityService.PrtProductTypeEntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
public class PrdProductService {

    private PrdProductEntityService prdProductEntityService;
    private PrtProductTypeEntityService prtProductTypeEntityService;

    public List<PrdProductResponseDto> findAll(){
        List<PrdProduct> prdProductList = prdProductEntityService.findAllWithControl();
        return PrdProductMapper.INSTANCE.convertToPrdProductResponseDtoList(prdProductList);
    }

    public List<PrdProductResponseDto> findAllByProductTypeId(Long productTypeId){
        prtProductTypeEntityService.existsByIdWithControl(productTypeId);
        return PrdProductMapper.INSTANCE.convertToPrdProductResponseDtoList(prdProductEntityService.findAllByProductTypeIdWithControl(productTypeId));
    }

    public List<PrdProductResponseDto> findAllBetweenMinAndMaxPrice(BigDecimal minPrice, BigDecimal maxPrice){
        prdProductEntityService.checkMinPriceAndMaxPriceIsValid(minPrice, maxPrice);
        return PrdProductMapper.INSTANCE.convertToPrdProductResponseDtoList(prdProductEntityService.findAllByInitialPriceBetweenMinPriceAndMaxPriceWithControl(minPrice,maxPrice));
    }

    public PrdProductResponseDto save(PrdProductDto prdProductDto){
        prdProductEntityService.checkExistByNameAndProductTypeId(prdProductDto.getName(), prdProductDto.getProductTypeId());

        PrtProductType prtProductType =  prtProductTypeEntityService.findByIdWithControl(prdProductDto.getProductTypeId());

        BigDecimal kdvAmount = evaluateKdvAmount(prdProductDto.getInitialPrice(), prtProductType.getKdv());

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductDto);
        prdProduct.setKdvAmount(kdvAmount);
        prdProduct.setLastPrice(prdProductDto.getInitialPrice().add(kdvAmount));

        return PrdProductMapper.INSTANCE.convertToPrdProductResponseDto(prdProductEntityService.save(prdProduct));
    }

    public PrdProductResponseDto update(Long id, PrdProductDto prdProductDto){
        PrdProduct prdProduct = prdProductEntityService.findByIdWithControl(id);
        PrtProductType prtProductType =  prtProductTypeEntityService.findByIdWithControl(prdProductDto.getProductTypeId());

        BigDecimal kdvAmount = new BigDecimal(BigInteger.ZERO);

        // Check product initial price or product type is changed if it is than evaluate new kdv amount
        if (detectKdvAmountShouldEvaluate(prdProduct, prdProductDto)){
            kdvAmount =  evaluateKdvAmount(prdProductDto.getInitialPrice(), prtProductType.getKdv());
        }

        prdProduct.setName(prdProductDto.getName());
        prdProduct.setInitialPrice(prdProductDto.getInitialPrice());
        prdProduct.setProductTypeId(prdProductDto.getProductTypeId());

        if (kdvAmount.compareTo(BigDecimal.ZERO) > 0){
            prdProduct.setKdvAmount(kdvAmount);
            prdProduct.setLastPrice(prdProductDto.getInitialPrice().add(kdvAmount));
        }

        return PrdProductMapper.INSTANCE.convertToPrdProductResponseDto(prdProductEntityService.save(prdProduct));
    }

    public void batchUpdate(Long productTypeId){
        PrtProductType prtProductType = prtProductTypeEntityService.findByIdWithControl(productTypeId);

        List<PrdProduct> prdProductList = prdProductEntityService.findAllByProductTypeIdWithControl(productTypeId);

        for (PrdProduct prdProduct : prdProductList){
            BigDecimal kdvAmount = evaluateKdvAmount(prdProduct.getInitialPrice(), prtProductType.getKdv());
            prdProduct.setKdvAmount(kdvAmount);
            prdProduct.setLastPrice(prdProduct.getInitialPrice().add(kdvAmount));
        }
        prdProductEntityService.saveALl(prdProductList);
    }

    public void delete(Long id){
        prdProductEntityService.deleteByIdWithControl(id);
    }

    private BigDecimal evaluateKdvAmount(BigDecimal initialPrice, BigDecimal kdv){
        if (initialPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new NotAcceptableExceptions(PrdProductErrorMessage.PRODUCT_INITIAL_PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
        if (kdv.compareTo(BigDecimal.ZERO) < 0){
            throw new NotAcceptableExceptions(PrtProductTypeErrorMessage.PRODUCT_TYPE_KDV_CAN_NOT_LESS_THAN_ZERO);
        }
        BigDecimal kdvAmount = initialPrice.multiply(kdv)
                .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);

        return kdvAmount;
    }

    private boolean detectKdvAmountShouldEvaluate(PrdProduct prdProduct, PrdProductDto prdProductDto){
        boolean shouldEvaluate = false;
        if (!prdProduct.getInitialPrice().equals(prdProductDto.getInitialPrice())
                || !prdProduct.getProductTypeId().equals(prdProductDto.getProductTypeId())){
            shouldEvaluate = true;
        }
        return shouldEvaluate;
    }
}
