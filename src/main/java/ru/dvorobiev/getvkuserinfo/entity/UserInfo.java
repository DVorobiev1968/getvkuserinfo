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
    private Long user_id;

    @Column(columnDefinition = "varchar(25)")
    private String user_f_name;

    @Column(columnDefinition = "varchar(25)")
    private String user_l_name;

    @Column(columnDefinition = "varchar(25)")
    private String user_city;

    @Column(columnDefinition = "varchar(15)")
    private String user_contacts;

    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "dd.mm.yyyy hh:mm:ss")
    private Date user_b_date;

}
