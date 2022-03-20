package com.softtechbootcamp.bitirme.app.prt.enums;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;

public enum PrtProductTypeErrorMessage implements BaseErrorMessage {
    PRODUCT_TYPE_KDV_CAN_NOT_LESS_THAN_ZERO("Not Acceptable Parameter", "Product type kdv can not be less than 0", "bitirme-200-406"),
    ;

    private  final String message;
    private  final String detailMessage;
    private  final String errorCode;

    PrtProductTypeErrorMessage(String message, String detailMessage, String errorCode){
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
