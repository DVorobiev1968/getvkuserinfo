package ru.dvorobiev.getvkuserinfo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;
import ru.dvorobiev.getvkuserinfo.service.VKService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
    private static final int PERIOD=1000;

    private final UserInfoService userInfoService;
    private final Conf conf;

    @Autowired
    private VKService vkService;

    public AppRunner(UserInfoService userInfoService, Conf conf) {
        this.userInfoService = userInfoService;
        this.conf = conf;
    }



    @Override
    public void run(String... args) throws Exception, InterruptedException {
        ScheduledExecutorService service= Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String errMessage;
                String nameThread=Thread.currentThread().getName();
                try {
                    errMessage = String.format("Thread %s running.", nameThread);
                    log.info(errMessage);
//                    vkService.getRandomVKUserWithSaveDB();
                    errMessage = String.format("Thread %s canceled!", nameThread);
                    log.info(errMessage);
                } catch (Exception e){
                    log.error("Run thread {}, Error: {}",nameThread, e.getMessage());
                }
                finally {
                    errMessage = "Thread: " + nameThread + " was finished";
                    log.info(errMessage);
                }

            }
        },0,3, TimeUnit.SECONDS);

        Thread.sleep(30000);
        service.shutdown();
        log.info("service.shutdown()");
        // второй способ часть реализации многопоточки
//        try {
//            threadMultiRead();
//        } catch (InterruptedException e) {
//            logger.error("Main thread was finished");
//        }
    }
}
