package ru.dvorobiev.getvkuserinfo.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvorobiev.getvkuserinfo.DTO.UserInfoDTO;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.repository.UserInfoRepository;

import java.util.List;

@Service
public class UserInfoService {
    public static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
    private final UserInfoRepository userInfoRepository;


    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
    @Transactional
    public UserInfo update(Long id, UserInfoDTO userInfoDTO){
        UserInfo userInfo=userInfoRepository.findById(id).orElse(null);
        if (userInfo !=null){
            userInfo.setUser_f_name(userInfoDTO.getUser_f_name());
            userInfo.setUser_l_name(userInfoDTO.getUser_l_name());
            userInfo.setUser_b_date(userInfoDTO.getUser_b_date());
            userInfo.setUser_city(userInfoDTO.getUser_city());
            userInfo.setUser_contacts(userInfoDTO.getUser_contacts());
            return userInfoRepository.save(userInfo);
        } else {
            String errMessage=String.format("Пользователя с таким ID:%d не существует",id);
            LOG.warn(errMessage);
            return null;
        }
    }
    @Transactional
    public List<UserInfo> getAll(){
        return userInfoRepository.findAll();
    }
}
