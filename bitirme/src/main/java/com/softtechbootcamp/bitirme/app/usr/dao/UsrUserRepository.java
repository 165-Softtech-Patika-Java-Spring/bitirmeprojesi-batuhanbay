package com.softtechbootcamp.bitirme.app.usr.dao;

import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsrUserRepository extends JpaRepository<UsrUser, Long> {
    Optional<UsrUser> findUsrUserByUsername(String username);

    @Query(value = "SELECT usrUser.id as id,"
            + " usrUser.username as username"
            + " FROM usr_user usrUser "
            + " WHERE usrUser.username = :username", nativeQuery = true)
    UsrUserUsernameAndId findUsrUserUsernameAndId(@Param("username") String username);

    boolean existsByUsername(String username);
}
