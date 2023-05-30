package ru.dvorobiev.getvkuserinfo;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.payload.response.UserInfoResponse;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;
import ru.dvorobiev.getvkuserinfo.service.VKService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class VKServiceTest {
    private final String user_id="290530455";
    @Autowired
    private VKService vkService;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private WebClient webClient;

    private UserInfoResponse userInfoResponse;
    private static UserInfoResponse expectedUser;

    @BeforeAll
    public static void initialize(){
        expectedUser=new UserInfoResponse(290530455,"Dmitry","Vorobyev", true, false);
    }

    @Test
    public void postUserIdTest() throws InterruptedException {
        Mono<String>userInfo=vkService.postUserInfo(expectedUser.getId());
        JsonElement jsonElement=new JsonParser().parse(userInfo.block());
        UserInfoResponse userInfoResponse=vkService.parseResponse(jsonElement);
        assertThat(userInfoResponse).isEqualTo(expectedUser);
    }

    @Test
    public void getUserIdWithErrorHandlingTest(){
        UserInfoResponse expectedUser=new UserInfoResponse(290530455,"Dmitry","Vorobyev", true, false);
        vkService.getUserInfoWithErrorHandling(expectedUser.getId())
                .subscribe(jsonString -> {
                    JsonElement jsonElement = new JsonParser().parse(jsonString);
                    UserInfoResponse userInfoResponse = vkService.parseResponse(jsonElement);
                    log.info("User info:{}", userInfoResponse);
                });
    }
}
