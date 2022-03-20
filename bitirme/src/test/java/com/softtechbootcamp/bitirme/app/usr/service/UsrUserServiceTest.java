package com.softtechbootcamp.bitirme.app.usr.service;

import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import com.softtechbootcamp.bitirme.app.usr.enums.UsrUserErrorMessage;
import com.softtechbootcamp.bitirme.app.usr.service.entityService.UsrUserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsrUserServiceTest {
    @Mock
    private UsrUserEntityService usrUserEntityService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UsrUserService usrUserService;

    @Test
    void shouldFindAllWhenUsrUserListExist() {
        List<UsrUser> userList = createDummyUsrUserList();

        List<UsrUserDto> expectedResult = createDummyTestUsrUserDtoList();

        when(usrUserEntityService.findAllWithControl()).thenReturn(userList);

        List<UsrUserDto> result = usrUserService.findAll();

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllWhenUsrUserDoesNotExist() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITIES_NOT_FOUND);

        when(usrUserEntityService.findAllWithControl()).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserService.findAll());

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }
    @Test
    void shouldFindByIdWhenUsrUserIdExist() {
        UsrUser usrUser = createDummyTestUsrUser();
        Long usrUserId = usrUser.getId();
        UsrUserDto expectedResult = createDummyTestUsrUserDto();

        when(usrUserEntityService.findByIdWithControl(usrUserId)).thenReturn(usrUser);

        UsrUserDto result = usrUserService.findById(usrUserId);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByIdWhenUsrUserIdDoesNotExist() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(usrUserEntityService.findByIdWithControl(anyLong())).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserService.findById(anyLong()));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldFindByUsernameWhenUsrUserHasUsername() {
        UsrUser usrUser = createDummyTestUsrUser();
        String usrUserUsername = usrUser.getUsername();
        UsrUserDto expectedResult = createDummyTestUsrUserDto();

        when(usrUserEntityService.findUsrUserByUsernameWithControl(usrUserUsername)).thenReturn(usrUser);

        UsrUserDto result = usrUserService.findByUsername(usrUserUsername);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByUsernameWhenUsrUserHasBlankUsername() {
        String blankUsername = "";

        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(UsrUserErrorMessage.HAS_BLANK_USERNAME_PARAMETER);

        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> usrUserService.findByUsername(blankUsername));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindByUsernameWhenUsernameDoesNotExist() {
        String doesNotExistUsername = "TestUsername";

        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(UsrUserErrorMessage.USER_NOT_FOUND_USERNAME);

        when(usrUserEntityService.findUsrUserByUsernameWithControl(doesNotExistUsername)).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserService.findByUsername(doesNotExistUsername));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);

    }

    @Test
    void shouldSaveWhenUsrUserDtoIsCorrect() {
        UsrUserDto usrUserDto = createDummyTestUsrUserDto();
        UsrUser usrUser = createDummyTestUsrUser();
        String encodedPassword = "EncodedPassword";

        when(usrUserEntityService.isExistUsername(usrUserDto.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(usrUserEntityService.save(any())).thenReturn(usrUser);

        UsrUserDto result = usrUserService.save(usrUserDto);

        assertEquals(usrUserDto, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveWhenUsrUserDtoHasDuplicateUsername() {
        UsrUserDto usrUserDto = createDummyTestUsrUserDto();
        String duplicateUsername = usrUserDto.getUsername();
        DuplicateEntityExceptions expectedException = new DuplicateEntityExceptions(UsrUserErrorMessage.HAS_DUPLICATE_USER_USERNAME);

        when(usrUserEntityService.isExistUsername(duplicateUsername)).thenReturn(true);

        DuplicateEntityExceptions result = assertThrows(DuplicateEntityExceptions.class, () -> usrUserService.save(usrUserDto));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenUsrUserDtoHasNonExistUsernameAndIdIsCorrect() {
        UsrUserDto usrUserDto = createDummyTestUsrUserDto();
        UsrUser usrUser = createDummyTestUsrUser();
        Long id = usrUser.getId();

        when(usrUserEntityService.findByIdWithControl(id)).thenReturn(usrUser);
        when(usrUserEntityService.getUsrUserUsernameAndIdWithControl(usrUserDto.getUsername())).thenReturn(null);
        when(usrUserEntityService.save(usrUser)).thenReturn(usrUser);

        UsrUserDto result = usrUserService.update(usrUserDto, id);

        assertEquals(usrUserDto, result);
        assertNotNull(result);
    }

    @Test
    void shouldUpdateWhenUsrUserDtoHasOwnUsernameAndIdIsCorrect() {
        UsrUserDto usrUserDto = createDummyTestUsrUserDto();
        UsrUser usrUser = createDummyTestUsrUser();
        UsrUserUsernameAndId usrUserUsernameAndId = createDummyUsrUserUsernameAndId(usrUser.getId(),usrUser.getUsername());
        Long id = usrUser.getId();

        when(usrUserEntityService.findByIdWithControl(id)).thenReturn(usrUser);
        when(usrUserEntityService.getUsrUserUsernameAndIdWithControl(usrUserDto.getUsername())).thenReturn(usrUserUsernameAndId);
        when(usrUserEntityService.save(usrUser)).thenReturn(usrUser);

        UsrUserDto result = usrUserService.update(usrUserDto, id);

        assertEquals(usrUserDto, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateWhenUsrUserDtoIsCorrectAndIdIsNonExist() {
        UsrUserDto usrUserDto = createDummyTestUsrUserDto();
        Long nonExistUsrUserId = 5L;
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);

        when(usrUserEntityService.findByIdWithControl(nonExistUsrUserId)).thenThrow(expectedException);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserService.update(usrUserDto, nonExistUsrUserId));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateWhenUsrUserDtoHasDuplicateUsernameAndIdIsCorrect() {
        UsrUserDto usrUserDto = createDummyTestUsrUserDto();
        UsrUser usrUser = createDummyTestUsrUser();
        UsrUserUsernameAndId usrUserUsernameAndId = createDummyUsrUserUsernameAndId(2L,usrUser.getUsername());
        Long id = usrUser.getId();

        DuplicateEntityExceptions expectedException = new DuplicateEntityExceptions(UsrUserErrorMessage.HAS_DUPLICATE_USER_USERNAME);

        when(usrUserEntityService.findByIdWithControl(id)).thenReturn(usrUser);
        when(usrUserEntityService.getUsrUserUsernameAndIdWithControl(usrUserDto.getUsername())).thenReturn(usrUserUsernameAndId);

        DuplicateEntityExceptions result = assertThrows(DuplicateEntityExceptions.class, () -> usrUserService.update(usrUserDto, id));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldDeleteWhenUsrUserIdIsExist() {
        doNothing().when(usrUserEntityService).deleteByIdWithControl(anyLong());
        usrUserService.delete(anyLong());
        verify(usrUserEntityService).deleteByIdWithControl(anyLong());
    }

    @Test
    void shouldNotDeleteWhenUsrUserIdNonExist() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);
        doThrow(new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND)).when(usrUserEntityService).deleteByIdWithControl(anyLong());

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserEntityService.deleteByIdWithControl(anyLong()));

        verify(usrUserEntityService).deleteByIdWithControl(anyLong());
        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    private UsrUser createDummyTestUsrUser(){
        UsrUser usrUser = new UsrUser();
        usrUser.setId(1L);
        usrUser.setName("testName");
        usrUser.setSurname("testSurname");
        usrUser.setUsername("testUsername");
        usrUser.setPassword("testPassword");
        return  usrUser;
    }

    private List<UsrUser> createDummyUsrUserList(){
        List<UsrUser>  usrUserList = new ArrayList<>();
        UsrUser dummyUsrUser = createDummyTestUsrUser();
        usrUserList.add(dummyUsrUser);
        return usrUserList;
    }

    private UsrUserDto createDummyTestUsrUserDto(){
        UsrUserDto usrUserDto = new UsrUserDto();
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

    private UsrUserUsernameAndId createDummyUsrUserUsernameAndId(Long id, String username){
        UsrUserUsernameAndId usrUserUsernameAndId = new UsrUserUsernameAndId() {
            @Override
            public Long getId() {
                return id;
            }

            @Override
            public String getUsername() {
                return username;
            }
        };
        return usrUserUsernameAndId;
    }
}