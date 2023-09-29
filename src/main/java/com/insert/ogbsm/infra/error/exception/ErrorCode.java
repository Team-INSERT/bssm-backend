package com.insert.ogbsm.infra.error.exception;

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
    NOT_SAME_USER(403, "USER-403-2", "Not A Same User"),
    USER_NOT_FOUND(404, "USER-404-1", "User Not Found"),

    //Bamboo
    BAMBOO_ALREADY_ALLOWED(400, "BAMBOO-400-1", "Bamboo Already Allowed"),
    BAMBOO_NOT_FOUND(404, "BAMBOO-404-1", "BAMBOO Not Found"),

    //JWT
    INVALID_TOKEN(403, "TOKEN-403-1", "Access with Invalid Token"),
    EXPIRED_JWT(403, "TOKEN-403-2", "Access Token Expired"),
    REFRESH_TOKEN_EXPIRED(403, "TOKEN-403-3", "Refresh Token Expired"),

    //ServerError,
    INVALID_ARGUMENT(400, "ARG-400-1", "Arg Is Not Valid"),
    NOT_FOUND(404, "NOT_FOUND", "Not Found"),
    BSM_AUTH_INVALID_CLIENT(500, "BSM-500-1", "Bsm Client Is Invalid"),
    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal Server Error"),

    //Post
    POST_TYPE_WEIRD(400, "POST-400-1", "Post Type Weird"),
    POST_VALUE_NOT_EXIST(400, "POST-400-2", "Post Value Not Exist"),
    POST_NOT_FOUND(404, "POST-404-1", "Post Not Found"),

    //Comment
    COMMENT_NOT_FOUND(404, "COMMENT-404-1", "Comment Not Found"),
    RECOMMENT_NOT_FOUND(404, "RECOMMENT-404-1", "ReComment Not Found"),

    // S3
    IMAGE_FAILED_SAVE(424, "IMAGE-424-1", "Image Failed Save"),
    IMAGE_NOT_FOUND(404, "IMAGE-404-1", "Image Not Found"),

    //Calender
    NO_AUTH_TO_DEF_CALENDER(403, "CALENDER-403-1", "No Auth To Def Calender"),
    CALENDER_NOT_FOUND(404, "CALENDER-404-1", "Calender Not Found"),
    Invalid_Date(400,"CALENDER-400-1","Invalid Date");

    private final int status;
    private final String code;
    private final String message;
}
