package ru.dvorobiev.getvkuserinfo.payload.response;

import lombok.Data;

@Data
public class City {
    private long id;
    private String title;

    public City(long id, String title) {
        this.id = id;
        this.title = title;
    }
}
