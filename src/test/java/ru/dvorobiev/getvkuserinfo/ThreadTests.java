package ru.dvorobiev.getvkuserinfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dvorobiev.getvkuserinfo.config.Conf;
import ru.dvorobiev.getvkuserinfo.service.VKService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class ThreadTests {
    @Autowired
    private Conf conf;

    @Autowired
    private VKService vkService;

    private static int countThread;

    @Test
    @DisplayName("threadTes")
    public void threadTest() throws InterruptedException {
        final long start=System.currentTimeMillis();

        int countThread= conf.getCountThread();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < countThread; i++) {
            Runnable worker=new ReadVKUserInfo(String.format("worker-%d",i),vkService);
            executorService.execute(worker);
        }
        Thread.sleep(10000);
        executorService.shutdown();
        log.info("Test elapsed time: {}",(System.currentTimeMillis()-start));
    }

    @Test
    @DisplayName("scheduleTesting")
    public void scheduleTest() throws InterruptedException {
        final long start=System.currentTimeMillis();
        int countThread= conf.getCountThread();

        ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String errMessage;
                String nameThread=Thread.currentThread().getName();
                try {
                    errMessage = String.format("Thread %s running.", nameThread);
                    log.info(errMessage);
                    vkService.getRandomVKUserWithSaveDB();
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
        log.info("Test elapsed time: {}",(System.currentTimeMillis()-start));
    }

    @Test
    @DisplayName("scheduleTesting")
    public void scheduleThreadPoolTest() throws InterruptedException {
        final long start=System.currentTimeMillis();
        int countThread= conf.getCountThread();

        ScheduledExecutorService service=Executors.newScheduledThreadPool(countThread);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String errMessage;
                String nameThread=Thread.currentThread().getName();
                try {
                    errMessage = String.format("Thread %s running.", nameThread);
                    log.info(errMessage);
                    vkService.getRandomVKUserWithSaveDB();
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
        log.info("Test elapsed time: {}",(System.currentTimeMillis()-start));
    }

    @Test
    @DisplayName("threadCachedTesting")
    public void threadCachedTesting() throws InterruptedException {
        final long start=System.currentTimeMillis();

        int countThread= conf.getCountThread();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < countThread; i++) {
            Runnable worker=new ReadVKUserInfo(String.format("worker-%d",i),vkService);
            executorService.execute(worker);
        }
        Thread.sleep(10000);
        executorService.shutdown();
        log.info("Test elapsed time: {}",(System.currentTimeMillis()-start));
    }
}
