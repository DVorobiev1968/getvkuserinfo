package ru.dvorobiev.getvkuserinfo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userCity;
    private String userContacts;
    private Date userBDate;
}
