package ru.dvorobiev.getvkuserinfo;

public class ErrorCode {
    // коды ошибок уровня Client API
    public static final int ERROR_API = 700;

    public static final int XLS_OK = ERROR_API + 3;
    public static final int XLS_ERR = ERROR_API - 1;
    public static final int XLS_EMPTY = ERROR_API - 2;

}
