package ru.dvorobiev.getvkuserinfo.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvorobiev.getvkuserinfo.ErrorCode;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.facade.UserInfoFacade;
import ru.dvorobiev.getvkuserinfo.payload.response.UserInfoResponse;
import ru.dvorobiev.getvkuserinfo.repository.UserInfoRepository;
import ru.dvorobiev.getvkuserinfo.utils.ReportExcel;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class UserInfoService {
    public static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
    private final UserInfoRepository userInfoRepository;
    private final UserInfoFacade userInfoFacade;
    private final Conf conf;


    public UserInfoService(UserInfoRepository userInfoRepository,
                           UserInfoFacade userInfoFacade,
                           Conf conf) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoFacade = userInfoFacade;
        this.conf = conf;
    }

    @Transactional
    public UserInfo update(Long id, UserInfoResponse response) throws ParseException {
        UserInfo userInfo = userInfoRepository.findById(id).orElse(null);
        try {
            if (userInfo != null) {
                userInfo = userInfoFacade.userInfoResponseToUserInfo(userInfo, response);
                userInfo = userInfoRepository.save(userInfo);
                String errMessage = String.format("Пользователя таким ID:%d обновлен успешно", id);
                LOG.info(errMessage);
                return userInfo;
            } else {
                String errMessage = String.format("Пользователя с таким ID:%d не существует", id);
                LOG.warn(errMessage);
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error update info for ID: {} {}", id, e.getMessage());
            return null;
        }
    }

    synchronized public List<UserInfo> getAll() {
        return userInfoRepository.findAll();
    }

    synchronized public int reporting(String nameSheet, List<UserInfo> userInfoList) throws IOException {
        try{
            ReportExcel reportExcel = new ReportExcel(conf.getExcelPathFileName(),nameSheet, userInfoList);
            int errno = reportExcel.createReport(conf.getExcelPathFileName());
            if (errno == ErrorCode.XLS_OK) {
                LOG.info("Отчет {} сформирован успешно", conf.getExcelPathFileName());
            } else {
                LOG.error("Ошибка при формировании отчета. Код: {}", errno);
            }
            return errno;
        } catch (Exception e){
            LOG.error("UserInfoService.reporting error: {}",e.getMessage());
            return ErrorCode.XLS_ERR;
        }
    }

}
