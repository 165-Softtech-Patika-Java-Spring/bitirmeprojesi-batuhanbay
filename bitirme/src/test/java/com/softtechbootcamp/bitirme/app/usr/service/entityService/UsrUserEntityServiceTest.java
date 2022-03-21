package com.softtechbootcamp.bitirme.app.usr.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.usr.dao.UsrUserRepository;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import com.softtechbootcamp.bitirme.app.usr.enums.UsrUserErrorMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsrUserEntityServiceTest {

    @Mock
    UsrUserRepository usrUserRepository;

    @InjectMocks
    private UsrUserEntityService usrUserEntityService;

    @Test
    void shouldFindUsrUserByUsernameWithControlWhenUsernameExist() {
        UsrUser usrUser = createDummyTestUsrUser();
        String existUsername = usrUser.getUsername();

        when(usrUserRepository.findUsrUserByUsername(existUsername)).thenReturn(Optional.of(usrUser));

        UsrUser result = usrUserEntityService.findUsrUserByUsernameWithControl(existUsername);

        assertEquals(usrUser, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindUsrUserByUsernameWithControlWhenUsernameDoesNotExist() {
        EntityNotFoundExceptions expectedException = new EntityNotFoundExceptions(UsrUserErrorMessage.USER_NOT_FOUND_USERNAME);

        EntityNotFoundExceptions result = assertThrows(EntityNotFoundExceptions.class, () -> usrUserEntityService.findUsrUserByUsernameWithControl(anyString()));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldGetUsrUserUsernameAndIdWithControlWhenUsernameIsCorrect() {
        String username = "Test";
        UsrUserUsernameAndId usrUserUsernameAndId = createDummyUsrUserUsernameAndId(1L,username);

        when(usrUserRepository.findUsrUserUsernameAndId(username)).thenReturn(usrUserUsernameAndId);

        UsrUserUsernameAndId result = usrUserEntityService.getUsrUserUsernameAndIdWithControl(username);

        assertEquals(usrUserUsernameAndId, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotGetUsrUserUsernameAndIdWithControlWhenUsernameIsNonExist() {
        NotAcceptableExceptions expectedException = new NotAcceptableExceptions(UsrUserErrorMessage.HAS_BLANK_USERNAME_PARAMETER);

        NotAcceptableExceptions result = assertThrows(NotAcceptableExceptions.class, () -> usrUserEntityService.getUsrUserUsernameAndIdWithControl(""));

        assertEquals(expectedException, result);
        assertEquals(expectedException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(expectedException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertEquals(expectedException.getBaseErrorMessage().getErrorCode(), result.getBaseErrorMessage().getErrorCode());
        assertNotNull(result);
    }

    @Test
    void shouldIsExistUsernameWhenUsernameIsExist() {
        when(usrUserRepository.existsByUsername(anyString())).thenReturn(true);

        boolean result = usrUserEntityService.isExistUsername(anyString());

        assertTrue(result);
    }

    @Test
    void shouldIsExistUsernameAndPasswordWhenUsernameAndPasswordIsValid(){
        String username = "Test";
        String password = "password";

        when(usrUserRepository.existsByUsernameAndPassword(username, password)).thenReturn(true);

        boolean result = usrUserEntityService.isExistUsernameAndPassword(username, password);

        assertTrue(result);
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