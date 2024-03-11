package com.vta.app.error;

public enum Api implements com.cloudimpl.error.core.ErrorCode {
    EMAIL_ALREADY_EXIST(1, "Email [email] already exists"),
    TOKEN_EXPIRED(3, "Token expired"),
    SOMETHING_WENT_WRONG(4, "Something went wrong"),
    USER_NOT_FOUND(5, "User [username] not found"),
    USER_PASSWORD_INCORRECT(6, "User [username] password incorrect"),
    VERIFICATION_CODE_NOT_FOUND(7, "Verification code not found"),
    VERIFICATION_CODE_EXPIRED(8, "Verification code expired"),
    VERIFICATION_CODE_DOES_NOT_MATCH(9, "Verification code does not match"),
    CONFIG_ALREADY_EXISTS(10, "Config [configName] already exists"),
    CONFIG_NOT_FOUND(11, "Config [configName] not found"),
    INVALID_MOBILE_NUMBER(12, "Invalid mobile number [number]"),
    USER_NOT_VERIFIED(13, "User [username] not verified"),
    HOTEL_NOT_FOUND(21, "Hotel [id] not found"),
    HOTEL_ALREADY_REGISTERED(22, "A Hotel is already registered to this account"),
    ;
    private final int errorNo ;
    private final String format ;

    @Override
    public int getErrorNo() {
        return this.errorNo ;
    }

    @Override
    public String getFormat() {
        return this.format ;
    }

    Api(int errorNo, String errorFormat) {
        this.errorNo = errorNo;
        this.format = errorFormat;
    }
}
