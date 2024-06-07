package com.vta.vtabackend.utils;

import lombok.Getter;

@Getter
public enum ErrorStatusCodes {
    GENERAL_ERROR(100, "An error occurred while processing the request"),
    USER_NOT_FOUND(101, "User not found for this email"),
    TOKEN_EXPIRED_PLEASE_TRY_AGAIN(102, "Token expired while please try again"),
    VERIFICATION_CODE_NOT_FOUND(103, "Verification code not found"),
    VERIFICATION_CODE_EXPIRED(104, "Verification code expired"),
    EMAIL_DOES_NOT_MATCH(105, "Email does not match"),
    INCORRECT_PASSWORD(106, "Incorrect password"),
    USER_NOT_VERIFIED(107, "User not verified"),
    YOU_ARE_NOT_ADMIN(108, "You are not admin"),
    MAX_FILE_SIZE(109, "Maximum file size is 2MB"),
    IMAGE_TYPE_NOT_SUPPORTED(110, "Image type not supported, Only jpg, png, gif, jpeg and bmp files are allowed"),
    FILE_UPLOAD_FAILED(111, "File upload failed"),
    HOTEL_NOT_FOUND(112, "Hotel not found"),
    BOOKING_FAILED(113, "Booking failed"),
    BOOKING_NOT_AVAILABLE(114, "Booking not available"),
    HOTEL_PROFILE_NOT_FOUND(115, "Hotel profile not found"),
    TOURGUIDE_NOT_FOUND(116, "Tourguide not found"),
    TOURPACKAGE_NOT_FOUND(117, "Tourpackage not found"),
    TRANSPORT_NOT_FOUND(118, "Transport not found");

    private final String message;

    private final Integer code;

    ErrorStatusCodes(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

}
