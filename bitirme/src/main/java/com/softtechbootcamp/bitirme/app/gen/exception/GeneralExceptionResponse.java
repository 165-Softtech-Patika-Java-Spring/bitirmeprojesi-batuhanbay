package com.softtechbootcamp.bitirme.app.gen.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralExceptionResponse {
    private Date errorDate;
    private String message;
    private String detailMessage;
    private int errorCode;
}
