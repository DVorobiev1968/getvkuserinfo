package ru.dvorobiev.getvkuserinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class UserInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="user_f_name", columnDefinition = "varchar(25)")
    private String userFirstName;

    @Column(name="user_l_name", columnDefinition = "varchar(25)")
    private String userLastName;

    @Column(name="user_city", columnDefinition = "varchar(25)")
    private String userCity;

    @Column(name="user_contacts",columnDefinition = "varchar(15)")
    private String userContacts;

    @Column(name="user_b_date", columnDefinition = "date")
    @JsonFormat(pattern = "dd.mm.yyyy hh:mm:ss")
    private Date userBDate;

}
