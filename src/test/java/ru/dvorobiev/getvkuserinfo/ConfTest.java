package ru.dvorobiev.getvkuserinfo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dvorobiev.getvkuserinfo.config.Conf;

@Slf4j
@SpringBootTest
public class ConfTest {
    @Autowired
    private Conf conf;

    @Test
    @DisplayName("getConfTest")
    public void getConfTest(){
        log.info(conf.toString());
    }
}
