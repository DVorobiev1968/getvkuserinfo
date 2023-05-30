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
    private boolean can_access_closed;
    private boolean is_closed;
}
