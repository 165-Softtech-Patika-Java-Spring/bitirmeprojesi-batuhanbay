package com.softtechbootcamp.bitirme.app.sec.security;

import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import com.softtechbootcamp.bitirme.app.usr.service.UsrUserService;
import com.softtechbootcamp.bitirme.app.usr.service.entityService.UsrUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UsrUserEntityService usrUserEntityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsrUser usrUser = usrUserEntityService.findUsrUserByUsernameWithControl(username);

        return JwtUserDetails.create(usrUser);
    }

    public UserDetails loadUserByUserId(Long id) {

        UsrUser usrUser = usrUserEntityService.findByIdWithControl(id);

        return JwtUserDetails.create(usrUser);
    }
}
