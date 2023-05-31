package ru.dvorobiev.getvkuserinfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.service.UserInfoService;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
@Slf4j
public class ThreadReadUserInfo implements Runnable {

    public static final int INIT_THREAD = 0;
    public static final int START_THREAD = 1;
    public static final int ERROR_THREAD = -1;
    public static final int RUN_THREAD = 2;
    public static final int CANCEL_THREAD = 3;
    public static final int ACTIVATE_THREAD = 4;

    private final String nameThread;
    private String errMessage;
    private int id;
    private int stateThread;

    private UserInfoService userInfoService;
    Thread thread;

    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean stopped = new AtomicBoolean(true);


    public ThreadReadUserInfo(String nameThread, UserInfoService userInfoService) {
        this.nameThread = nameThread;
        this.stateThread = INIT_THREAD;
        this.userInfoService=userInfoService;
    }

    public void interrupt() {
        running.set(false);
        thread.interrupt();
    }

    boolean isRunning() {
        return running.get();
    }

    boolean isStopped() {
        return stopped.get();
    }


    @Override
    public void run(){
        try {
            this.stateThread = RUN_THREAD;
            this.running.set(true);
            this.stopped.set(false);

            errMessage = String.format("Thread %s running.", nameThread);
            log.info(errMessage);
            long counter = 0L;
            List<UserInfo> userInfoList=userInfoService.getAll();
            int errno=userInfoService.reporting(nameThread,userInfoList);
            this.stateThread = CANCEL_THREAD;
            errMessage = String.format("Thread %s canceled!", nameThread);
            log.info(errMessage);
            stopped.set(true);
        } catch (Exception e){
            log.error("Run thread {}, Error: {}",nameThread, e.getMessage());
        }
        finally {
            errMessage = "Thread: " + this.nameThread + " was finished";
            log.info(errMessage);
        }
    }

    public void start() {
        errMessage = String.format("Thread %s started...", nameThread);
        if (thread == null) {
            this.stateThread = START_THREAD;
            thread = new Thread(this, nameThread);
            errMessage = String.format("Thread %s new started...", nameThread);
            thread.start();
        }
        log.info(errMessage);
    }
}
