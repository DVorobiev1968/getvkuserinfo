package ru.dvorobiev.getvkuserinfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dvorobiev.getvkuserinfo.entity.UserInfo;
import ru.dvorobiev.getvkuserinfo.service.VKService;

@Data
@Slf4j
public class ReadVKUserInfo implements Runnable {

    private final String nameThread;
    private String errMessage;
    private int id;
    private VKService vkService;

    public ReadVKUserInfo(String nameThread, VKService vkService) {
        this.nameThread = nameThread;
        this.vkService = vkService;
    }

    @Override
    public void run(){
        try {
            errMessage = String.format("Thread %s running.", nameThread);
            log.info(errMessage);
//            UserInfo userInfo=vkService.getRandomVKUserWithSaveDB().get();
//            errMessage = String.format("Thread %s canceled! UserInfo: %s", nameThread,userInfo.toString());
            errMessage = String.format("Thread %s canceled!", nameThread);
            log.info(errMessage);
        } catch (Exception e){
            log.error("Run thread {}, Error: {}",nameThread, e.getMessage());
        }
        finally {
            errMessage = "Thread: " + this.nameThread + " was finished";
            log.info(errMessage);
        }
    }
}
