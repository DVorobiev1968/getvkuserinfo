package ru.dvorobiev.getvkuserinfo.facade;

import org.springframework.stereotype.Component;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.payload.response.UserInfoResponse;
import ru.dvorobiev.getvkuserinfo.utils.DateFormatted;

import java.text.ParseException;

@Component
public class UserInfoFacade {
    public UserInfoDTO userInfoToUserInfoDTO(UserInfo userInfo){
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        userInfoDTO.setUserId(userInfo.getUserId());
        userInfoDTO.setUserFirstName(userInfo.getUserFirstName());
        userInfoDTO.setUserLastName(userInfo.getUserLastName());
        userInfoDTO.setUserBDate(userInfo.getUserBDate());
        userInfoDTO.setUserContacts(userInfo.getUserContacts());
        return userInfoDTO;
    }
    public UserInfo userInfoResponseToUserInfo(UserInfo userInfo, UserInfoResponse userInfoResponse) throws ParseException {
        if (userInfoResponse.getCity() !=null){
            userInfo.setUserCity(userInfoResponse.getCity().getTitle());
        } else {
            userInfo.setUserCity("");
        }
        userInfo.setUserContacts(userInfoResponse.getMobilePhone());
        userInfo.setUserFirstName(userInfoResponse.getFirstName());
        userInfo.setUserLastName(userInfoResponse.getLastName());
        userInfo.setUserBDate(DateFormatted.perfomDate(userInfoResponse.getBdate()));
        return userInfo;
    }
}
