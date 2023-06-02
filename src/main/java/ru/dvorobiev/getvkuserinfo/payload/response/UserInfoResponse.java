package ru.dvorobiev.getvkuserinfo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String bdate;
    private City city;
    private String mobilePhone;
    private boolean canAccessClosed;
    private boolean isClosed;

    public UserInfoResponse(long id, String firstName, String lastName, String bdate, String mobilePhone, boolean canAccessClosed, boolean isClosed) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bdate = bdate;
        this.city = new City(0l,"");
        this.mobilePhone = mobilePhone;
        this.canAccessClosed = canAccessClosed;
        this.isClosed = isClosed;
    }
}

