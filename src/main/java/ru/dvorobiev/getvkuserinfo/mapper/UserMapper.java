package ru.dvorobiev.getvkuserinfo.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;

@Mapper
public interface UserMapper {
    UserInfoDTO map(UserInfo userInfo);

    @InheritInverseConfiguration
    UserInfo map(UserInfoDTO userInfoDTO);
}
