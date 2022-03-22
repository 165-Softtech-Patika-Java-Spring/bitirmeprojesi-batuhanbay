package com.softtechbootcamp.bitirme.app.gen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class BaseTest {
    protected ObjectMapper objectMapper;

    protected boolean isSuccess(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        GeneralResponse generalResponse = getRestResponse(result);

        return isSuccess(generalResponse);
    }

    protected GeneralResponse getRestResponse(MvcResult result) throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), GeneralResponse.class);
    }

    private boolean isSuccess(GeneralResponse generalResponse) {
        return generalResponse.isSuccess();
    }
}
