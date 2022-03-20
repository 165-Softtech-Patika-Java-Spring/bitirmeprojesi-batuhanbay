package com.softtechbootcamp.bitirme.app.gen.exceptions;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEntityExceptions extends BusinessExceptions{

    public DuplicateEntityExceptions(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }

}