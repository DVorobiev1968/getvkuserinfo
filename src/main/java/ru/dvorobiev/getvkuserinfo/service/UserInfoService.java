package ru.dvorobiev.getvkuserinfo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dvorobiev.getvkuserinfo.ErrorCode;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.repository.UserInfoRepository;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final Conf conf;


    public Mono<UserInfo> add(UserInfo userInfo){
        return userInfoRepository.save(
                userInfo.toBuilder()
                .userBDate(userInfo.getUserBDate())
                .userId(userInfo.getUserId())
                .id(userInfo.getId())
                .userCity(userInfo.getUserCity())
                .userContacts(userInfo.getUserContacts())
                .userFirstName(userInfo.getUserFirstName())
                .userLastName(userInfo.getUserLastName())
                .build()
        ).doOnSuccess(user->{
            log.info("user with id: {} added success",user.getUserId());
        }).doOnError(error->{
            log.error("Error add user with id: {} Error: {}",userInfo.getUserId(),error.getMessage());
            });
    }

    public Flux<UserInfo> getAll() {
        return userInfoRepository.findAll();
    }

    public int reporting(String nameSheet) throws IOException {
        try {
            Flux<UserInfo>infoFlux=getAll();
            infoFlux.subscribe(
                (data) -> {
                    log.info("reporting of: {}", data);
                },
                (err)-> log.error("error"+err),
                ()->log.info("cancel"))
            .dispose();
//            ReportExcel reportExcel = new ReportExcel(conf.getExcelPathFileName(),nameSheet, userInfoList);
//            int errno = reportExcel.createReport(conf.getExcelPathFileName());
//            if (errno == ErrorCode.XLS_OK) {
//                log.info("Report {} created success", conf.getExcelPathFileName());
//            } else {
//                log.error("Error while generating report. Code error: {}", errno);
//            }
//            return errno;
//        } catch (Exception e){
//            log.error("UserInfoService.reporting error: {}",e.getMessage());
//            return ErrorCode.XLS_ERR;
//        }
            return ErrorCode.XLS_OK;
        } catch (Exception e) {
            log.error("UserInfoService.reporting error: {}", e.getMessage());
            return ErrorCode.XLS_ERR;
        }
    }
}
