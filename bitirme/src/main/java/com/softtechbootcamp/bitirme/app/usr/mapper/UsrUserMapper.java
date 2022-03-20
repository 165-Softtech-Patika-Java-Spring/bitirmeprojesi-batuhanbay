package com.softtechbootcamp.bitirme.app.usr.mapper;

import com.softtechbootcamp.bitirme.app.usr.dto.UsrUserDto;
import com.softtechbootcamp.bitirme.app.usr.entity.UsrUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UsrUserMapper {
    UsrUserMapper INSTANCE = Mappers.getMapper(UsrUserMapper.class);

    UsrUser convertToUsrUser(UsrUserDto usrUserDto);
    UsrUserDto convertToUsrUserDto(UsrUser usrUser);
    List<UsrUserDto> convertToUsrUserDtoList(List<UsrUser> usrUserList);
}
