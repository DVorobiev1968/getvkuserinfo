package ru.dvorobiev.getvkuserinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.mapper.UserMapper;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;
import ru.dvorobiev.getvkuserinfo.utils.DateFormatted;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest
public class UserInfoServiceTest {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Conf conf;

    @Test
    @DisplayName("userInfoServiceGetAllTest")
    public void userInfoServiceGetAllTest() {

    }
    @Test
    public void userAddTest(){
        long randomNumber= DateFormatted.getRandomNumber(conf.getStartRandomUserId().intValue(),
                conf.getStartRandomUserId().intValue()*10);

        UserInfo user=userMapper.map(new UserInfoDTO(randomNumber,
                "Test",
                "Testing",
                "Perm",
                "+7XXXXXXXXXX",
                LocalDate.now()));

        userInfoService.add(user).block();
    }
    @Test
    public void userGetAllTest() throws IOException, InterruptedException {
        userInfoService.reporting("test");
        Thread.sleep(3000);
    }
    @Test
    public void userDTOGetAll() throws InterruptedException {
        userInfoService
            .getAllDTO()
            .subscribe(
                (data) -> {
                    log.info("getDTOclass of: {}", data);
                },
                (err)-> log.error("error: "+err),
                ()->log.info("cancel"));
        Thread.sleep(3000);
    }
}
