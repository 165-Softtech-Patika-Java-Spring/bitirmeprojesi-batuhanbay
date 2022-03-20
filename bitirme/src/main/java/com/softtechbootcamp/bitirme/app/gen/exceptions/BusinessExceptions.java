package com.softtechbootcamp.bitirme.app.gen.exceptions;

import com.softtechbootcamp.bitirme.app.gen.enums.BaseErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessExceptions extends RuntimeException{
    private BaseErrorMessage baseErrorMessage;
}
