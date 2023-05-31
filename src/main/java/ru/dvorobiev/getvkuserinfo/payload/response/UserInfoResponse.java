package ru.dvorobiev.getvkuserinfo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private long id;
    private String first_name;
    private String last_name;
    private String bdate;
    private City city;
    private String mobile_phone;
    private boolean can_access_closed;
    private boolean is_closed;

    public UserInfoResponse(long id, String first_name, String last_name, String bdate, String mobile_phone, boolean can_access_closed, boolean is_closed) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.bdate = bdate;
        this.city = new City(0l,"");
        this.mobile_phone = mobile_phone;
        this.can_access_closed = can_access_closed;
        this.is_closed = is_closed;
    }
}

