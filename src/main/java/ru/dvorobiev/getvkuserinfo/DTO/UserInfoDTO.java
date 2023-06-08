package ru.dvorobiev.getvkuserinfo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDTO {
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userCity;
    private String userContacts;
    @JsonFormat(pattern = "dd.mm.yyyy hh:mm:ss")
    private Date userBDate;
}
