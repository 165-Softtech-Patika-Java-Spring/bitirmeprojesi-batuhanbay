package com.softtechbootcamp.bitirme.app.usr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtechbootcamp.bitirme.BitirmeApplication;
import com.softtechbootcamp.bitirme.app.config.H2TestProfileJPAConfig;
import com.softtechbootcamp.bitirme.app.gen.BaseTest;
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


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {BitirmeApplication.class, H2TestProfileJPAConfig.class})
class UsrUserControllerIntegrationTest extends BaseTest {

    private static final String BASE_PATH = "/users";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllWhenUsrUserIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldFindByIdWhenUsrUserIdIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/2").content("2L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotFindByIdWhenUsrUserIdIsNotExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/9").content("9L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldFindByUsernameWhenUsrUserUsernameIsExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/by/username/TestUsername3").content("TestUsername3").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotFindByUsernameWhenUsrUserUsernameIsNotExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/by/username/NotExistUsername").content("NotExistUsername").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldSaveWhenUsrUserDtoIsValid() throws Exception {
        UsrUserDto usrUserDto = new UsrUserDto();
        usrUserDto.setName("Test");
        usrUserDto.setSurname("Test");
        usrUserDto.setUsername("TestUsername");
        usrUserDto.setPassword("123456");

        String content = objectMapper.writeValueAsString(usrUserDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotSaveWhenUsrUserDtoUsernameIsAlreadyExist() throws Exception {
        UsrUserDto usrUserDto = new UsrUserDto();
        usrUserDto.setName("Test3");
        usrUserDto.setSurname("Test3");
        usrUserDto.setUsername("TestUsername1");
        usrUserDto.setPassword("123456");

        String content = objectMapper.writeValueAsString(usrUserDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH).content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldUpdateWhenUsrUserIdIsExistAndUserUserDtoIsValid() throws Exception {
        UsrUserDto usrUserDto = new UsrUserDto();
        usrUserDto.setName("Test");
        usrUserDto.setSurname("Test2");
        usrUserDto.setUsername("TestUsername4");
        usrUserDto.setPassword("123456");

        String content = objectMapper.writeValueAsString(usrUserDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/2").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotUpdateWhenUsrUserIdIsNotExistAndUserUserDtoIsValid() throws Exception {
        UsrUserDto usrUserDto = new UsrUserDto();
        usrUserDto.setName("Test");
        usrUserDto.setSurname("Test2");
        usrUserDto.setUsername("TestUsername2");
        usrUserDto.setPassword("123456");

        String content = objectMapper.writeValueAsString(usrUserDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/99999").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotUpdateWhenUsrUserIdIsExistAndUserUserDtoUsernameIsAlreadyExist() throws Exception {
        UsrUserDto usrUserDto = new UsrUserDto();
        usrUserDto.setName("Test4");
        usrUserDto.setSurname("Test4");
        usrUserDto.setUsername("TestUsername2");
        usrUserDto.setPassword("123456");

        String content = objectMapper.writeValueAsString(usrUserDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/4").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldDeleteWhenUsrUserIdIsExist() throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/2").content("2").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNoDeleteWhenUsrUserIdIsNotExist() throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/9999").content("9999").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }
}