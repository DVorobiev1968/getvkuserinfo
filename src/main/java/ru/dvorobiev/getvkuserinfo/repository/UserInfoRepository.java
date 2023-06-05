package ru.dvorobiev.getvkuserinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Override
    Optional<UserInfo> findById(Long id);

    Optional<UserInfo>findUserInfoById(Long id);
}
