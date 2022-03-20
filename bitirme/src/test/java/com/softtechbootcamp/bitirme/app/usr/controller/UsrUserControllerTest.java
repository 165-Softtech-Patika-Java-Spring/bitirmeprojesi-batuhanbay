package com.softtechbootcamp.bitirme.app.usr.controller;

import com.softtechbootcamp.bitirme.app.gen.dto.GeneralResponse;
import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import com.softtechbootcamp.bitirme.app.usr.enums.UsrUserErrorMessage;
import com.softtechbootcamp.bitirme.app.usr.mapper.UsrUserMapper;
import com.softtechbootcamp.bitirme.app.usr.service.UsrUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsrUserControllerTest {

    @Mock
    private UsrUserService usrUserService;

    @InjectMocks
    private UsrUserController usrUserController;

    @Test
    void shouldFindAllWhenUsrUsersExist() {
        List<UsrUserDto> dummyUsrUserDtoList = createDummyTestUsrUserDtoList();

        when(usrUserService.findAll()).thenReturn(dummyUsrUserDtoList);

        ResponseEntity<GeneralResponse<List<UsrUserDto>>> result = (ResponseEntity<GeneralResponse<List<UsrUserDto>>>) usrUserController.findAll();

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyUsrUserDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllWhenUsrUsersDoesNotExist() {

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITIES_NOT_FOUND);

        when(usrUserService.findAll()).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserController.findAll());

        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldFindByIdWhenUsrUserExistWithId() {
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();

        when(usrUserService.findById(1L)).thenReturn(dummyUsrUserDto);

        ResponseEntity<GeneralResponse<UsrUserDto>> result = (ResponseEntity<GeneralResponse<UsrUserDto>>) usrUserController.findById(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyUsrUserDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByIdWhenUsrUserDoesNotExistWithId() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(usrUserService.findById(anyLong())).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserController.findById(anyLong()));

        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldFindByUsernameWhenUsrUserExistWithUsername() {
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();
        String testUsername = dummyUsrUserDto.getUsername();

        when(usrUserService.findByUsername(testUsername)).thenReturn(dummyUsrUserDto);

        ResponseEntity<GeneralResponse<UsrUserDto>> result = (ResponseEntity<GeneralResponse<UsrUserDto>>) usrUserController.findByUsername(testUsername);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyUsrUserDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByUsernameWhenUsrUserDoesNotExistWithUsername() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(UsrUserErrorMessage.USER_NOT_FOUND_USERNAME);

        when(usrUserService.findByUsername(anyString())).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserController.findByUsername(anyString()));

        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByUsernameWhenUsrUserDoesWithBlankUsername() {
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(UsrUserErrorMessage.HAS_BLANK_USERNAME_PARAMETER);
        String testBlankUsername = " ";

        when(usrUserService.findByUsername(testBlankUsername)).thenThrow(expectedException);

        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> usrUserController.findByUsername(testBlankUsername));

        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldSaveWhenUsrUserDtoCorrect() {
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();

        when(usrUserService.save(dummyUsrUserDto)).thenReturn(dummyUsrUserDto);

        ResponseEntity<GeneralResponse<UsrUserDto>> result = (ResponseEntity<GeneralResponse<UsrUserDto>>) usrUserController.save(dummyUsrUserDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyUsrUserDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveWhenUsrUserDtoHasDuplicateUsername() {
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();

        DuplicateEntityExceptions expectedException = new DuplicateEntityExceptions(UsrUserErrorMessage.HAS_DUPLICATE_USER_USERNAME);

        when(usrUserService.save(dummyUsrUserDto)).thenThrow(expectedException);

        DuplicateEntityExceptions result = assertThrows(DuplicateEntityExceptions.class, () -> usrUserController.save(dummyUsrUserDto));


        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenUsrUserDtoCorrect() {
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();
        Long dummyUsrUserDtoId = 1L;

        when(usrUserService.update(dummyUsrUserDto, dummyUsrUserDtoId)).thenReturn(dummyUsrUserDto);

        ResponseEntity<GeneralResponse<UsrUserDto>> result = (ResponseEntity<GeneralResponse<UsrUserDto>>) usrUserController.update(dummyUsrUserDto, dummyUsrUserDtoId);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyUsrUserDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateWhenUsrUserDtoUsernameDuplicate() {
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();
        Long dummyUsrUserDtoId = 1L;

        DuplicateEntityExceptions expectedException = new DuplicateEntityExceptions(UsrUserErrorMessage.HAS_DUPLICATE_USER_USERNAME);

        when(usrUserService.update(dummyUsrUserDto, dummyUsrUserDtoId)).thenThrow(expectedException);

        DuplicateEntityExceptions result = assertThrows(DuplicateEntityExceptions.class, () -> usrUserController.update(dummyUsrUserDto, dummyUsrUserDtoId));


        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);

    }

    @Test
    void shouldDeleteWhenUsrUserDtoIdExist() {
        doNothing().when(usrUserService).delete(anyLong());
        ResponseEntity<GeneralResponse<UsrUserDto>> result = (ResponseEntity<GeneralResponse<UsrUserDto>>) usrUserController.delete(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private UsrUserDto createDummyTestUsrUserDto(){
        UsrUserDto usrUserDto = mock(UsrUserDto.class);
        usrUserDto.setName("testName");
        usrUserDto.setSurname("testSurname");
        usrUserDto.setUsername("testUsername");
        usrUserDto.setPassword("testPassword");
        return  usrUserDto;
    }

    private List<UsrUserDto> createDummyTestUsrUserDtoList(){
        List<UsrUserDto>  usrUserDtoList = new ArrayList<>();
        UsrUserDto dummyUsrUserDto = createDummyTestUsrUserDto();
        usrUserDtoList.add(dummyUsrUserDto);
        return usrUserDtoList;
    }
}