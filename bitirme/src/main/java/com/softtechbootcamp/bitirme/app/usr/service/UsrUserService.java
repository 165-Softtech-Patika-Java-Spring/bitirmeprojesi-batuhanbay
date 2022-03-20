package com.softtechbootcamp.bitirme.app.usr.service;

import com.softtechbootcamp.bitirme.app.gen.exceptions.DuplicateEntityExceptions;
import com.softtechbootcamp.bitirme.app.gen.exceptions.NotAcceptableExceptions;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserUsernameAndId;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import com.softtechbootcamp.bitirme.app.usr.enums.UsrUserErrorMessage;
import com.softtechbootcamp.bitirme.app.usr.mapper.UsrUserMapper;
import com.softtechbootcamp.bitirme.app.usr.service.entityService.UsrUserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsrUserService {
    private UsrUserEntityService usrUserEntityService;
    private PasswordEncoder passwordEncoder;

    public List<UsrUserDto> findAll(){
        List<UsrUser> userList = usrUserEntityService.findAllWithControl();
        return UsrUserMapper.INSTANCE.convertToUsrUserDtoList(userList);
    }

    public UsrUserDto findById(Long id){
        UsrUser usrUser = usrUserEntityService.findByIdWithControl(id);
        return UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);
    }

    public UsrUserDto findByUsername(String username){
        if (username.isBlank()){
            throw new NotAcceptableExceptions(UsrUserErrorMessage.HAS_BLANK_USERNAME_PARAMETER);
        }
        UsrUser usrUser = usrUserEntityService.findUsrUserByUsernameWithControl(username);

        return UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUser);
    }

    public UsrUserDto save(UsrUserDto usrUserDto){
        boolean isExistUsername = usrUserEntityService.isExistUsername(usrUserDto.getUsername());
        if (isExistUsername){
            throw new DuplicateEntityExceptions(UsrUserErrorMessage.HAS_DUPLICATE_USER_USERNAME);
        }
        UsrUser usrUser = UsrUserMapper.INSTANCE.convertToUsrUser(usrUserDto);
        String password = passwordEncoder.encode(usrUserDto.getPassword());
        usrUser.setPassword(password);
        return  UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUserEntityService.save(usrUser));
    }

    public UsrUserDto update(UsrUserDto usrUserDto, Long id){
        UsrUser usrUser = usrUserEntityService.findByIdWithControl(id);

        UsrUserUsernameAndId usrUserUsernameAndId =  usrUserEntityService.getUsrUserUsernameAndIdWithControl(usrUserDto.getUsername());

        if (usrUserUsernameAndId != null && usrUserUsernameAndId.getUsername().equals(usrUserDto.getUsername())
                && !usrUser.getId().equals(usrUserUsernameAndId.getId())){
            throw new DuplicateEntityExceptions(UsrUserErrorMessage.HAS_DUPLICATE_USER_USERNAME);
        }

        usrUser.setUsername(usrUserDto.getUsername());
        usrUser.setPassword(usrUserDto.getPassword());
        usrUser.setName(usrUserDto.getName());
        usrUser.setSurname(usrUserDto.getSurname());
        return UsrUserMapper.INSTANCE.convertToUsrUserDto(usrUserEntityService.save(usrUser));
    }

    public void delete(Long id){
        usrUserEntityService.deleteByIdWithControl(id);
    }

}
