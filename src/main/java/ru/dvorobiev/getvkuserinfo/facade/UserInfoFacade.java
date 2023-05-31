package ru.dvorobiev.getvkuserinfo.facade;

import org.springframework.stereotype.Component;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.payload.response.UserInfoResponse;
import ru.dvorobiev.getvkuserinfo.utils.DateFormatted;

import java.text.ParseException;
import java.util.Date;

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
    public UserInfo userInfoResponseToUserInfo(UserInfo userInfo, UserInfoResponse userInfoResponse) throws ParseException {
        if (userInfoResponse.getCity() !=null){
            userInfo.setUser_city(userInfoResponse.getCity().getTitle());
        } else {
            userInfo.setUser_city("");
        }
        userInfo.setUser_contacts(userInfoResponse.getMobile_phone());
        userInfo.setUser_f_name(userInfoResponse.getFirst_name());
        userInfo.setUser_l_name(userInfoResponse.getLast_name());
        userInfo.setUser_b_date(DateFormatted.perfomDate(userInfoResponse.getBdate()));
        return userInfo;
    }
}
