package com.softtechbootcamp.bitirme.app.rpt.enums;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;

public enum ExportReportErrorMessage implements BaseErrorMessage {
    UNEXPECTED_ERROR_DURING_CREATE_FILE("Occurred Unexpected Error", "An error occurred while creating a file in the directory where the certificate to be generated will be saved.", "bitirme-700-500"),
    NOT_ENOUGH_FREE_SPACE_ON_DISC("Not Enough Space", "There is no free space on the disk of the directory where the report will be saved.", "bitirme-701-500"),
    COULD_NOT_FIND_LOGO_PATH("Could Not Find", "The logos to be used in the report are not found in the required directory.", "bitirme-702-500"),
    UNEXPECTED_ERROR_DURING_GENERATING_REPORT("Occurred Unexpected Error", "An error occurred while creating a report.", "bitirme-702-404"),
    COULD_NOT_FIND_TEMPLATE_JRXML("Could Not Find", "Could not find report template jrxml file.", "bitirme-702-404");


    private  final String message;
    private  final String detailMessage;
    private  final String errorCode;

    ExportReportErrorMessage(String message, String detailMessage, String errorCode){
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
