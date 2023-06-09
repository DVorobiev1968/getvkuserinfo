package ru.dvorobiev.getvkuserinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("user_info")
public class UserInfo {
    @Id
    private Long id;
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
