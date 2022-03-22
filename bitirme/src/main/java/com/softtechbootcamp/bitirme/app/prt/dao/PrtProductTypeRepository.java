package com.softtechbootcamp.bitirme.app.prt.dao;

import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDetailDto;
import com.softtechbootcamp.bitirme.app.prt.entity.PrtProductType;
import com.softtechbootcamp.bitirme.app.prt.enums.PrtProductTypeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrtProductTypeRepository extends JpaRepository<PrtProductType, Long> {

    @Query(value = "SELECT MIN(prdProduct.last_price) as minPrice,"
            + " MAX(prdProduct.last_price) as maxPrice,"
            + " AVG(prdProduct.last_price) as averagePrice,"
            + " productType.kdv as kdv,"
            + " productType.name as productTypeName,"
            + " COUNT(prdProduct.id_product_type) as productTypeCount"
            + " FROM prt_product_type productType "
            + " LEFT OUTER JOIN  prd_product prdProduct ON (prdProduct.id_product_type = productType.id)"
            + " WHERE productType.id = :id", nativeQuery = true)
    PrtProductTypeDetailDto findProductDetail(@Param("id") Long id);
}
