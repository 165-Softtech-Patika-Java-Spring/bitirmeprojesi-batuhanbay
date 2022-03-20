package com.softtechbootcamp.bitirme.app.prd.enums;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;

public enum PrdProductErrorMessage implements BaseErrorMessage {
    HAS_DUPLICATE_PRODUCT_NAME_IN_PRODUCT_TYPE("Encounter A Conflict", "There is already product name with this product type.", "bitirme-300-409"),
    PRODUCT_INITIAL_PRICE_MUST_BE_GREATER_THAN_ZERO("Not Acceptable Parameter", "Product initial price must be greater than 0", "bitirme-301-406"),
    PRODUCT_MIN_PRICE_MUST_BE_GREATER_THAN_ZERO("Not Acceptable Parameter", "Minimum price must be greater than 0", "bitirme-302-406"),
    PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_ZERO("Not Acceptable Parameter", "Maximum price must be greater than 0", "bitirme-303-406"),
    PRODUCT_MAX_PRICE_MUST_BE_GREATER_THAN_MIN_PRICE("Not Acceptable Parameter", "Maximum price must be greater than minimum price", "bitirme-304-406"),
    NOT_FOUND_PRODUCT_LIST_WITH_PRODUCT_TYPE("Entities Not Found", "There are not  any product with this product type", "bitirme-305-404"),
    NOT_FOUND_PRODUCT_LIST_BETWEEN_MIN_AND_MAX_PRICE("Entities Not Found", "There are not any product between minimum and maximum price", "bitirme-306-404"),
    ;

    private  final String message;
    private  final String detailMessage;
    private  final String errorCode;

    PrdProductErrorMessage(String message, String detailMessage, String errorCode){
        this.message = message;
        this.detailMessage = detailMessage;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
