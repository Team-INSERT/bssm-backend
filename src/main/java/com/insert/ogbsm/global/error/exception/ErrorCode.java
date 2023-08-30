package com.insert.ogbsm.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    FORBIDDEN(403, "COMMON-403-1", "Forbidden"),

    //User
    USER_NOT_LOGIN(403, "USER-403-1", "User Not Login"),
    USER_NOT_FOUND(404, "USER-404-1", "User Not Found"),

    //Bamboo
    BAMBOO_ALREADY_ALLOWED(400, "BAMBOO-400-1", "Bamboo Already Allowed"),

    //JWT
    INVALID_TOKEN(403, "TOKEN-403-1", "Access with Invalid Token"),
    EXPIRED_JWT(403, "TOKEN-403-2", "Access Token Expired"),
    REFRESH_TOKEN_EXPIRED(403, "TOKEN-403-3", "Refresh Token Expired"),

    //ServerError,
    INVALID_ARGUMENT(400, "ARG-400-1", "Arg Is Not Valid"),
    BSM_AUTH_INVALID_CLIENT(500, "BSM-500-1", "Bsm Client Is Invalid"),
    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal Server Error");

    private final int status;
    private final String code;
    private final String message;
}
