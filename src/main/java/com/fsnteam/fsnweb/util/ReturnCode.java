package com.fsnteam.fsnweb.util;

public enum  ReturnCode implements IReturnCode {
    SUCCESS(200,"Success"),
    LOST(400,"RESOURCE LOST"),
    SERVERERROR(500,"Server error"),
    USER_ACCOUNT_DISABLE(308,"account is forbidden"),
    ARITHMETIC_EXCEPTION(501,"ARITHMETIC_EXCEPTION"),
    OPTION_FAILED(502,"Invalid option"),
    USER_NOT_FOUND(301,"can't find this user"),
    USER_CREDENTIALS_ERROR(302,"password incorrect"),
    USER_ACCOUNT_EXPIRED(303,"USER_ACCOUNT_EXPIRED"),
    USER_ACCOUNT_NOT_EXIST(304,"USER_ACCOUNT_NOT_EXIST"),
    COMMON_FAIL(305,"COMMON_FAIL"),
    USER_ACCOUNT_LOCKED(306,"USER_ACCOUNT_LOCKED"),
    AUTHORITY_ERROR(307,"DON'T HAVE AUTHORITY");

    private Integer code;

    private String message;

    ReturnCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
