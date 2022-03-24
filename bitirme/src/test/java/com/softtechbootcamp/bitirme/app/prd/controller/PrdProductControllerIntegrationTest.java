package com.softtechbootcamp.bitirme.app.prd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtechbootcamp.bitirme.BitirmeApplication;
import com.softtechbootcamp.bitirme.app.config.H2TestProfileJPAConfig;
import com.softtechbootcamp.bitirme.app.gen.BaseTest;
import com.softtechbootcamp.bitirme.app.prd.dto.PrdProductDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {BitirmeApplication.class, H2TestProfileJPAConfig.class})
class PrdProductControllerIntegrationTest extends BaseTest {

    private static final String BASE_PATH = "/products";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllWhenPrdProductIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldFindAllByProductTypeIdWhenPrdProductIdIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotFindAllByProductTypeIdWhenPrdProductIdIsNotExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/99").content("99L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldFindAllPrdProductsBetweenWhenPrdProductIsExistBetweenMinAndMaxPrice() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/between")
                        .param("minPrice", "1")
                        .param("maxPrice", "200")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotFindAllPrdProductsBetweenWhenPrdProductIsExistBetweenMinAndMaxPrice() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/between")
                        .param("minPrice", "500")
                        .param("maxPrice", "900")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldSaveWhenPrdProductDtoIsValid() throws Exception {
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("Phone");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(1L);

        String content = objectMapper.writeValueAsString(prdProductDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotSaveWhenPrdProductDtoNameIsAlreadyExistWithPrtProductType() throws Exception {
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("Test2");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(1L);

        String content = objectMapper.writeValueAsString(prdProductDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotSaveWhenPrdProductDtoValidAndPrtProductTypeIdIsNotExist() throws Exception {
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("Laptop");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(11L);

        String content = objectMapper.writeValueAsString(prdProductDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldUpdateWhenPrdProductDtoIsValidAndPrdProductIdIsExist() throws Exception {
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("Updated Test1");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(1L);

        String content = objectMapper.writeValueAsString(prdProductDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/3").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotUpdateWhenPrdProductDtoIsValidAndPrdProductIdIsNotExist() throws Exception {
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("updated test");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(1L);

        String content = objectMapper.writeValueAsString(prdProductDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/15").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotUpdateWhenPrdProductDtoIsValidAndPrtProductTypeIdIsNotExist() throws Exception {
        PrdProductDto prdProductDto = new PrdProductDto();
        prdProductDto.setName("updated test");
        prdProductDto.setInitialPrice(BigDecimal.TEN);
        prdProductDto.setProductTypeId(15L);

        String content = objectMapper.writeValueAsString(prdProductDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/1").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldDeleteWhenPrdProductIdIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/5").content("5").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotDeleteWhenPrdProductIdIsNotExist() throws Exception {
        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/15").content("15").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }
}