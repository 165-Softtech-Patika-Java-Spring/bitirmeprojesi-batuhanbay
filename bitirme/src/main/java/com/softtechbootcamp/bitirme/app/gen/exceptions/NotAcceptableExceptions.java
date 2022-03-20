package com.softtechbootcamp.bitirme.app.gen.exceptions;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableExceptions extends BusinessExceptions {

    public NotAcceptableExceptions(BaseErrorMessage baseErrorMessage){
        super(baseErrorMessage);
    }

}
