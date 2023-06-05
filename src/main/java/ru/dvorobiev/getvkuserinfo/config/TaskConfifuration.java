package ru.dvorobiev.getvkuserinfo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class TaskConfifuration {
    private Conf conf;

    public TaskConfifuration(Conf conf) {
        this.conf = conf;
    }

    @Bean
    public Executor taskExecutor(){
        log.debug("Creating Async TaskExecutor");
        final ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(conf.getCountThread());
        executor.setMaxPoolSize(conf.getCountThread());
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("VK_Thread-");
        executor.initialize();
        return executor;
    }
}
