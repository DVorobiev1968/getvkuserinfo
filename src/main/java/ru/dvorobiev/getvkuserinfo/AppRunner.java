package ru.dvorobiev.getvkuserinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);
    private static final int PERIOD=1000;

    private final UserInfoService userInfoService;
    private final Conf conf;

    public AppRunner(UserInfoService userInfoService, Conf conf) {
        this.userInfoService = userInfoService;
        this.conf = conf;
    }

    public void threadMultiRead() throws InterruptedException {
        List<ThreadReadUserInfo> threadReadUserInfos = new ArrayList<ThreadReadUserInfo>();
        for (int i = 0; i < conf.getCountThread(); i++) {
            threadReadUserInfos.add(new ThreadReadUserInfo(String.format("TESTING_%d", i),
                    this.userInfoService));
        }
        threadReadUserInfos.forEach((threadReadUserInfo -> {
            threadReadUserInfo.start();
        }));
        threadReadUserInfos.forEach((threadReadUserInfo -> {
            try {
                if (threadReadUserInfo.getStateThread() < ThreadReadUserInfo.CANCEL_THREAD) {
                    Thread.sleep(PERIOD);
                } else {
                    if (threadReadUserInfo.getNameThread()!=null){
                        logger.info(String.format("Thread %s was finished", threadReadUserInfo.getNameThread()));
                    }
                }
            } catch (InterruptedException exception) {
                logger.error(exception.getMessage());
            }
        }));
    }

    @Override
    public void run(String... args) throws Exception, InterruptedException {
        try {
            threadMultiRead();
        } catch (InterruptedException e) {
            logger.error("Main thread was finished");
        }
    }
}
