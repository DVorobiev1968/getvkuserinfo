package ru.dvorobiev.getvkuserinfo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userCity;
    private String userContacts;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy",
            timezone = "+0500")
    private LocalDate userBDate;
}
