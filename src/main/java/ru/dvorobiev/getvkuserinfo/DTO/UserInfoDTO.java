package ru.dvorobiev.getvkuserinfo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {
    private Long user_id;
    private String user_f_name;
    private String user_l_name;
    private String user_city;
    private String user_contacts;
    private Date user_b_date;
}
