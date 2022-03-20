package com.softtechbootcamp.bitirme.app.gen.exception;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.gen.exceptions.BusinessExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest){

        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = webRequest.getDescription(false);


        GeneralExceptionResponse generalExceptionResponse = createGeneralExceptionResponse(errorDate,message,description,HttpStatus.INTERNAL_SERVER_ERROR.value());

        GeneralResponse<GeneralExceptionResponse> generalResponse = GeneralResponse.error(generalExceptionResponse);
        generalResponse.setMessages(message);


        String logErrorMessage = handleLogErrorMessage(message, description, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        log.error(logErrorMessage);

        return new ResponseEntity<>(generalResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllBusinessExceptions(BusinessExceptions ex){
        Date errorDate = new Date();
        String message = ex.getBaseErrorMessage().getMessage();
        String detailMessage = ex.getBaseErrorMessage().getDetailMessage();
        String errorCode = ex.getBaseErrorMessage().getErrorCode();

        GeneralExceptionResponse generalExceptionResponse = createGeneralExceptionResponse(errorDate,message,detailMessage,HttpStatus.INTERNAL_SERVER_ERROR.value());

        GeneralResponse<GeneralExceptionResponse> generalResponse = GeneralResponse.error(generalExceptionResponse);
        generalResponse.setMessages(errorCode);

        String logErrorMessage = handleLogErrorMessage(message, detailMessage, errorCode);
        log.error(logErrorMessage);

        return new ResponseEntity<>(generalResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDuplicateEntityExceptions(DuplicateEntityExceptions ex){

        Date errorDate = getCurrentDate();
        String message = ex.getBaseErrorMessage().getMessage();
        String detailMessage = ex.getBaseErrorMessage().getDetailMessage();
        String errorCode = ex.getBaseErrorMessage().getErrorCode();

        GeneralExceptionResponse generalExceptionResponse = createGeneralExceptionResponse(errorDate,message,detailMessage,HttpStatus.CONFLICT.value());

        GeneralResponse<GeneralExceptionResponse> generalResponse = GeneralResponse.error(generalExceptionResponse);
        generalResponse.setMessages(errorCode);

        String logErrorMessage = handleLogErrorMessage(message, detailMessage, errorCode);
        log.error(logErrorMessage);

        return new ResponseEntity<>(generalResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final  ResponseEntity<Object> handleEntityNotFoundExceptions(EntityNotFoundExceptions ex){

        Date errorDate = getCurrentDate();
        String message = ex.getBaseErrorMessage().getMessage();
        String detailMessage = ex.getBaseErrorMessage().getDetailMessage();
        String errorCode = ex.getBaseErrorMessage().getErrorCode();

        GeneralExceptionResponse generalExceptionResponse = createGeneralExceptionResponse(errorDate,message,detailMessage,HttpStatus.NOT_FOUND.value());

        GeneralResponse<GeneralExceptionResponse> generalResponse = GeneralResponse.error(generalExceptionResponse);
        generalResponse.setMessages(errorCode);


        String logErrorMessage = handleLogErrorMessage(message, detailMessage, errorCode);
        log.error(logErrorMessage);

        return new ResponseEntity<>(generalResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleNotAcceptableExceptions(NotAcceptableExceptions ex){

        Date errorDate = getCurrentDate();
        String message = ex.getBaseErrorMessage().getMessage();
        String detailMessage = ex.getBaseErrorMessage().getDetailMessage();
        String errorCode = ex.getBaseErrorMessage().getErrorCode();

        createGeneralExceptionResponse(errorDate,message,detailMessage,HttpStatus.NOT_ACCEPTABLE.value());
        GeneralExceptionResponse generalExceptionResponse = createGeneralExceptionResponse(errorDate,message,detailMessage,HttpStatus.NOT_ACCEPTABLE.value());

        GeneralResponse<GeneralExceptionResponse> generalResponse = GeneralResponse.error(generalExceptionResponse);
        generalResponse.setMessages(errorCode);

        String logErrorMessage = handleLogErrorMessage(message, detailMessage, errorCode);
        log.error(logErrorMessage);

        return new ResponseEntity<>(generalResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Date errorDate = getCurrentDate();
        String message = new StringBuilder(ex.getBindingResult().getFieldError().getField()).append(" field not valid").toString();
        String detailMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        GeneralExceptionResponse generalExceptionResponse = createGeneralExceptionResponse(errorDate,message,detailMessage,status.value());

        GeneralResponse<GeneralExceptionResponse> restResponse = GeneralResponse.error(generalExceptionResponse);
        restResponse.setMessages(message);

        String logErrorMessage = handleLogErrorMessage(message, detailMessage, String.valueOf(status.value()));
        log.error(logErrorMessage);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    private String handleLogErrorMessage(String message, String detailMessage, String errorCode){

        String logErrorMessage = new StringBuilder("Message: ").append(message)
                .append(", Detail Message: ").append(detailMessage).append(", Error Code: ").append(errorCode).toString();

        return  logErrorMessage;
    }

    private GeneralExceptionResponse createGeneralExceptionResponse(Date errorDate, String message, String detailMessage, int httpStatusCode){
        return new GeneralExceptionResponse(errorDate,message,detailMessage,httpStatusCode);

    }

    private Date getCurrentDate(){
        return new Date();
    }
}
