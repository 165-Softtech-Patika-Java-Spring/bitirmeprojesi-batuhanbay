package com.softtechbootcamp.bitirme.app.gen.exceptions;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundExceptions extends  BusinessExceptions{

    public EntityNotFoundExceptions(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }

}
