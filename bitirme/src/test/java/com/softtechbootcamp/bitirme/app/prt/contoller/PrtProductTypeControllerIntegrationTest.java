package com.softtechbootcamp.bitirme.app.prt.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtechbootcamp.bitirme.BitirmeApplication;
import com.softtechbootcamp.bitirme.app.config.H2TestProfileJPAConfig;
import com.softtechbootcamp.bitirme.app.gen.BaseTest;
import com.softtechbootcamp.bitirme.app.prt.dto.PrtProductTypeDto;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {BitirmeApplication.class, H2TestProfileJPAConfig.class})
class PrtProductTypeControllerIntegrationTest extends BaseTest {

    private static final String BASE_PATH = "/products/types";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllWhenPrtProductTypeExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldFindProductDetailWhenPrtProductTypeIdIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1/details").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotFindProductDetailWhenPrtProductTypeIdIsNotExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/99/details").content("99L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldUpdateWhenPrtProductTypeIdIsExist() throws Exception {
        PrtProductTypeDto prtProductTypeDto = new PrtProductTypeDto();
        prtProductTypeDto.setKdv(BigDecimal.TEN);

        String content = objectMapper.writeValueAsString(prtProductTypeDto);

        MvcResult result = mockMvc.perform(
                patch(BASE_PATH + "/1").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotUpdateWhenPrtProductTypeIdIsNotExist() throws Exception {
        PrtProductTypeDto prtProductTypeDto = new PrtProductTypeDto();
        prtProductTypeDto.setKdv(BigDecimal.TEN);

        String content = objectMapper.writeValueAsString(prtProductTypeDto);

        MvcResult result = mockMvc.perform(
                patch(BASE_PATH + "/99").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }
}