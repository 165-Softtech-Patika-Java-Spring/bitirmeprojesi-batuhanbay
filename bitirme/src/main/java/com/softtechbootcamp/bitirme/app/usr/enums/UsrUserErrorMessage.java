package com.softtechbootcamp.bitirme.app.usr.enums;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;

public enum UsrUserErrorMessage implements BaseErrorMessage {
    USER_NOT_FOUND_USERNAME("Entities Could Not Found", "There are not any saved user with this username.", "bitirme-100-404"),
    USER_LOGIN_FAILED("Login Failed", "Username or password is not correct. Please try again!", "bitirme-101-404"),
    HAS_DUPLICATE_USER_USERNAME("Encounter A Conflict", "This username is already used for another user.", "bitirme-102-409"),
    HAS_BLANK_USERNAME_PARAMETER("Encounter a Blank Parameter", "Username parameter can not be blank", "bitirme-103-400"),;

    private  final String message;
    private  final String detailMessage;
    private  final String errorCode;

    UsrUserErrorMessage(String message, String detailMessage, String errorCode){
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
