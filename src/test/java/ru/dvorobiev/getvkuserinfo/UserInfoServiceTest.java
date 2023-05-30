package ru.dvorobiev.getvkuserinfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.facade.UserInfoFacade;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;

import java.util.List;

@Slf4j
@SpringBootTest
public class UserInfoServiceTest {
    @Autowired
    private UserInfoService userInfoService;

    @Test
    @DisplayName("userInfoServiceGetAllTest")
    public void userInfoServiceGetAllTest() {
        UserInfoFacade userInfoFacade=new UserInfoFacade();
        List<UserInfo> userInfoList=userInfoService.getAll();
        for (UserInfo userInfo:userInfoList){
            UserInfoDTO userInfoDTO=userInfoFacade.userInfoToUserInfoDTO(userInfo);
            log.info(userInfoDTO.toString());
        }

    }
}
