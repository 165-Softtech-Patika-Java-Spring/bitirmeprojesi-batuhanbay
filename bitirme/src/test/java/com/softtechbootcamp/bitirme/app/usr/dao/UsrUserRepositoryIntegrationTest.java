package com.softtechbootcamp.bitirme.app.usr.dao;

import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import org.h2.engine.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsrUserRepositoryIntegrationTest {

    @Autowired
    private UsrUserRepository usrUserRepository;

    @Test
    void findUsrUserByUsername() {
        String username = "TestUsername1";

        Optional<UsrUser> user = usrUserRepository.findUsrUserByUsername(username);

        assertNotNull(user);
        assertEquals(username, user.get().getUsername());
    }

    @Test
    void findUsrUserUsernameAndId() {
        String username = "TestUsername1";
        Long id = 2L;

        UsrUserUsernameAndId usrUserUsernameAndId = usrUserRepository.findUsrUserUsernameAndId(username);

        assertNotNull(usrUserUsernameAndId);
        assertEquals(username, usrUserUsernameAndId.getUsername());
        assertEquals(id, usrUserUsernameAndId.getId());
    }

    @Test
    void existsByUsername() {
        String username = "TestUsername1";
        boolean isExist = usrUserRepository.existsByUsername(username);

        assertTrue(isExist);
    }
}