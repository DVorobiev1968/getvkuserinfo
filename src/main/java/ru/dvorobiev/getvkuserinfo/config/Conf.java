package ru.dvorobiev.getvkuserinfo.config;

import lombok.Data;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class Conf implements EnvironmentAware {
    private int countThread;
    private String access_token;
    private String excelPathFileName;
    private String apiVersion;
    private String base_url;
    private String fields;

    @Override
    public void setEnvironment(Environment environment) {
        this.countThread = Integer.parseInt(environment.getProperty("application.countThread"));
        this.access_token = environment.getProperty("vk.access_token");
        this.excelPathFileName = environment.getProperty("excel.filename");
        this.apiVersion=environment.getProperty("vk.api.version");
        this.base_url=environment.getProperty("vk.base_url");
        this.fields=environment.getProperty("vk.fields");

    }

    @Override
    public String toString() {
        return "Conf{" +
                "countThread=" + countThread +
                ", access_token='" + access_token + '\'' +
                ", excelPathFileName='" + excelPathFileName + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", base_url='" + base_url + '\'' +
                ", fields='" + fields + '\'' +
                '}';
    }
}
