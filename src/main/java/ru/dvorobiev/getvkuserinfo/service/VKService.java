package ru.dvorobiev.getvkuserinfo.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.payload.response.UserInfoResponse;
import ru.dvorobiev.getvkuserinfo.utils.DateFormatted;

import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Data
@Service
@Slf4j
public class VKService {
    private final Conf conf;
    private final WebClient webClient;
    private String url;
    private UserInfoService userInfoService;

    public VKService(Conf conf, WebClient webClient, UserInfoService userInfoService) {
        this.conf = conf;
        this.webClient = webClient;
        this.userInfoService=userInfoService;
    }

//    @Async
//    public CompletableFuture<UserInfo>getRandomVKUserWithSaveDB() throws InterruptedException, ParseException {
//        final long start=System.currentTimeMillis();
//        long randomNumber= DateFormatted.getRandomNumber(conf.getStartRandomUserId().intValue(),
//                conf.getStartRandomUserId().intValue()*10);
//        Mono<String>userInfo=postUserInfo(randomNumber);
//        JsonElement jsonElement=new JsonParser().parse(userInfo.block());
//        UserInfoResponse userInfoResponse=parseResponse(jsonElement);
//
//        UserInfo user=userInfoService.addWithUpdate(randomNumber,userInfoResponse);
//        log.info("Save VK user ID`s: {}({}) info (elapsed time: {})",randomNumber,
//                user.getUserId(),
//                (System.currentTimeMillis()-start));
//        return CompletableFuture.completedFuture(user);
//    }

//    @Async
//    public CompletableFuture<UserInfo> getRandomVKUser() throws InterruptedException, ParseException {
//        final long start=System.currentTimeMillis();
//        long randomNumber= DateFormatted.getRandomNumber(conf.getStartRandomUserId().intValue(),
//                conf.getStartRandomUserId().intValue()*10);
//        Mono<String>userInfo=postUserInfo(randomNumber);
//        JsonElement jsonElement=new JsonParser().parse(userInfo.block());
//        UserInfoResponse userInfoResponse=parseResponse(jsonElement);
//        UserInfo user=userInfoService.add(userInfoResponse);
//        log.info("Save VK user info (elapsed time: {})",(System.currentTimeMillis()-start));
//        return CompletableFuture.completedFuture(user);
//    }
    private void setUrlAPI(long id) {
        this.url = String.format("%s?access_token=%s&user_ids=%d&fields=%s&v=%s",
                conf.getBase_url(),
                conf.getAccess_token(),
                id,
                conf.getFields(),
                conf.getApiVersion());
    }
//
//    public UserInfoResponse parseResponse(JsonElement jsonElement){
//        try {
//            JsonObject jsonObject=jsonElement.getAsJsonObject();
//            String jsonResponse=jsonObject.get("response").toString();
//            jsonResponse=jsonResponse.replace("]","");
//            jsonResponse=jsonResponse.replace("[","");
//            Gson gson=new Gson();
//            UserInfoResponse userInfoResponse=gson.fromJson(jsonResponse,UserInfoResponse.class);
//            return userInfoResponse;
//        } catch (Exception e) {
//            log.error("Error parsing response {}",e.getMessage());
//            UserInfoResponse infoResponse=new UserInfoResponse();
//            log.info("Info user: {}", infoResponse);
//            return infoResponse;
//        }
//    }
    private ExchangeFilterFunction logResponseStatus(){
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            switch (clientResponse.statusCode().value()){
                case 200:
                    log.info("Response status {}",clientResponse.statusCode());
                    break;
                default:
                    log.warn("An error has occurred {}",clientResponse.statusCode());
                    break;
            }
            return Mono.just(clientResponse);
        });
    }

    public Mono<String> postUserInfo(Long id) throws InterruptedException {
        setUrlAPI(id);
        Mono<String> response = WebClient
                .builder()
                .filter(logResponseStatus())
                .build()
                .post()
                .uri(this.url)
                .retrieve()
                .bodyToMono(String.class);
        Thread.sleep(1000);
        return response;
    }

    public Mono<String> getUserInfoWithErrorHandling(long id) throws InterruptedException {
        setUrlAPI(id);
        Mono<String> response=WebClient
                .builder()
                .build()
                .post()
                .uri(this.url)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        error -> Mono.error(new RuntimeException("ID not found")))
                .onStatus(HttpStatus.SERVICE_UNAVAILABLE::equals,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(String.class);
        Thread.sleep(1000);
        return response;
    }
}
