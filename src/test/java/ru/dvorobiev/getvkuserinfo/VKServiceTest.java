package ru.dvorobiev.getvkuserinfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.payload.response.City;
import ru.dvorobiev.getvkuserinfo.payload.response.UserInfoResponse;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;
import ru.dvorobiev.getvkuserinfo.service.VKService;
import ru.dvorobiev.getvkuserinfo.utils.ReportExcel;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class VKServiceTest {
    @Autowired
    private VKService vkService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private Conf conf;

    private static UserInfoResponse expectedUser;


    @BeforeAll
    public static void initialize(){
        expectedUser=new UserInfoResponse(
                290530455,
                "Dmitry",
                "Vorobyev",
                "2.10.1968",
                new City(4590, "Dobryanka"),
                "",
                true,
                false);
    }
    @Test
    @Ignore
    public void postUserIdTest() throws InterruptedException {
        Mono<String>userInfo=vkService.postUserInfo(expectedUser.getId());
        JsonElement jsonElement=new JsonParser().parse(userInfo.block());
        UserInfoResponse userInfoResponse=vkService.parseResponse(jsonElement);
        assertThat(userInfoResponse).isEqualTo(expectedUser);
    }
    private void updateDB(Long id){
        try{
            Mono<String>userInfo=vkService.postUserInfo(id);
            JsonElement jsonElement=new JsonParser().parse(userInfo.block());
            UserInfoResponse userInfoResponse=vkService.parseResponse(jsonElement);
            userInfoService.update(id,userInfoResponse);
        } catch (Exception e) {
            log.error("Error update for ID: {}: {}",id, e.getMessage());
        }
    }
    @Test
    @Ignore
    public void postUserIdWithUpdateDBTest() throws InterruptedException, ParseException {
        try{
            Mono<String>userInfo=vkService.postUserInfo(expectedUser.getId());
            JsonElement jsonElement=new JsonParser().parse(userInfo.block());
            UserInfoResponse userInfoResponse=vkService.parseResponse(jsonElement);
            userInfoService.update(expectedUser.getId(),userInfoResponse);
        } catch (Exception e) {
            log.error("Error postUserIdWithUpdateDBTest: {}",e.getMessage());
        }
    }

    @Test
    @Ignore
    public void getUserIdWithErrorHandlingTest(){
        vkService.getUserInfoWithErrorHandling(expectedUser.getId())
                .subscribe(jsonString -> {
                    JsonElement jsonElement = new JsonParser().parse(jsonString);
                    UserInfoResponse userInfoResponse = vkService.parseResponse(jsonElement);
                    log.info("User info:{}", userInfoResponse);
                });
    }

    @Test
    public void updateAllUsers(){
        List<UserInfo> userInfoList=userInfoService.getAll();
        for (UserInfo userInfo: userInfoList){
            updateDB(userInfo.getUserId());
        }
    }
    @Test void reporting() throws IOException {
        List<UserInfo> userInfoList=userInfoService.getAll();
        ReportExcel reportExcel=new ReportExcel(conf.getExcelPathFileName(),"testing",userInfoList);
        int errno=reportExcel.createReport(conf.getExcelPathFileName());
        log.info("reporting {}",errno);

    }

}
