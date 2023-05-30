package ru.dvorobiev.getvkuserinfo.facade;

import org.springframework.stereotype.Component;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;

@Component
public class UserInfoFacade {
    public UserInfoDTO userInfoToUserInfoDTO(UserInfo userInfo){
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        userInfoDTO.setUser_id(userInfo.getUser_id());
        userInfoDTO.setUser_f_name(userInfo.getUser_f_name());
        userInfoDTO.setUser_l_name(userInfo.getUser_l_name());
        userInfoDTO.setUser_b_date(userInfo.getUser_b_date());
        userInfoDTO.setUser_contacts(userInfo.getUser_contacts());
        return userInfoDTO;
    }
}
