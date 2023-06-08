package ru.dvorobiev.getvkuserinfo.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;

@Repository
public interface UserInfoRepository extends R2dbcRepository<UserInfo, Long> {
    Mono<UserInfo> findById(Long id);
    Mono<UserInfo> findUserInfoById(Long id);
    Flux<UserInfo> findAll();
}
