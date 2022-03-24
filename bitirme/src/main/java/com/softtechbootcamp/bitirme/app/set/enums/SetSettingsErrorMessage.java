package com.softtechbootcamp.bitirme.app.set.enums;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;

public enum SetSettingsErrorMessage implements BaseErrorMessage {

    SETTINGS_NOT_FOUND("Entity Not Found", "There is not saved directory path for printed report", "bitirme-600-406"),
    ;

    private  final String message;
    private  final String detailMessage;
    private  final String errorCode;

    SetSettingsErrorMessage(String message, String detailMessage, String errorCode){
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
