package com.softtechbootcamp.bitirme.app.usr.dao;

import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsrUserRepositoryIntegrationTest {

    @Autowired
    private UsrUserRepository usrUserRepository;

    @Test
    void findUsrUserByUsername() {
        String username = "TestUsername6";

        Optional<UsrUser> user = usrUserRepository.findUsrUserByUsername(username);

        assertNotNull(user);
    }

    @Test
    void findUsrUserUsernameAndId() {
        String username = "TestUsername6";
        Long id = 7L;

        UsrUserUsernameAndId usrUserUsernameAndId = usrUserRepository.findUsrUserUsernameAndId(username);

        assertNotNull(usrUserUsernameAndId);
        assertEquals(username, usrUserUsernameAndId.getUsername());
        assertEquals(id, usrUserUsernameAndId.getId());
    }

    @Test
    void existsByUsername() {
        String username = "TestUsername6";
        boolean isExist = usrUserRepository.existsByUsername(username);

        assertTrue(isExist);
    }
}