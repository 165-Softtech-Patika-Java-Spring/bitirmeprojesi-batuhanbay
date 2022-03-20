package com.softtechbootcamp.bitirme.app.gen.enums;

public enum GeneralErrorMessage implements  BaseErrorMessage{

    ENTITY_NOT_FOUND("bitirme-1-404","Entity not found!", "Entity could not find with this id."),
    ENTITIES_NOT_FOUND("bitirme-2-404","Entities Could Not Found", "There are not any saved entities."),
    INVALID_REQUEST("bitirme-3-400", "Invalid parameter", "The request which sent with parameters is incorrect."),
    INTERNAL_SERVER_ERROR("bitirme-4-500", "Encounter internal server", "Server encountered an unexpected condition that prevented it from fulfilling the request"),
    ;

    private  final String message;
    private  final String detailMessage;
    private  final String errorCode;

    GeneralErrorMessage(String errorCode, String message, String detailMessage) {
        this.errorCode = errorCode;
        this.message = message;
        this.detailMessage = detailMessage;
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
