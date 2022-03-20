package com.softtechbootcamp.bitirme.app.usr.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.gen.service.BaseEntityService;
import com.softtechbootcamp.bitirme.app.usr.dao.UsrUserRepository;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import com.softtechbootcamp.bitirme.app.usr.enums.UsrUserErrorMessage;
import org.springframework.stereotype.Service;

@Service
public class UsrUserEntityService extends BaseEntityService<UsrUser, UsrUserRepository> {
    public UsrUserEntityService(UsrUserRepository eRepository) {
        super(eRepository);
    }

    public UsrUser findUsrUserByUsernameWithControl(String username){
        UsrUser usrUser = getRepository().findUsrUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundExceptions(UsrUserErrorMessage.USER_NOT_FOUND_USERNAME));
        return  usrUser;
    }

    public UsrUserUsernameAndId getUsrUserUsernameAndIdWithControl(String username){
        if (username.isBlank()){
            throw new NotAcceptableExceptions(UsrUserErrorMessage.HAS_BLANK_USERNAME_PARAMETER);
        }
        UsrUserUsernameAndId usrUserUsernameAndId = getRepository().findUsrUserUsernameAndId(username);

        return usrUserUsernameAndId;
    }

    public boolean isExistUsername(String username){
        return getRepository().existsByUsername(username);
    }


}
