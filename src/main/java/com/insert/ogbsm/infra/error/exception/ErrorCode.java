package com.insert.ogbsm.infra.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    //User
    USER_NOT_LOGIN(403, "USER-403-1", "User Not Login"),
    NOT_SAME_USER(403, "USER-403-2", "Not A Same User"),
    USER_NOT_FOUND(404, "USER-404-1", "User Not Found"),

    //Student
    STUDENT_NOT_FOUND(404, "STUDENT-404-1", "Student Not Found"),

    //Bamboo
    BAMBOO_ALREADY_ALLOWED(400, "BAMBOO-400-1", "Bamboo Already Allowed"),
    BAMBOO_NOT_FOUND(404, "BAMBOO-404-1", "BAMBOO Not Found"),

    //Post
    POST_TYPE_WEIRD(400, "POST-400-1", "Post Type Weird"),
    POST_VALUE_NOT_EXIST(400, "POST-400-2", "Post Value Not Exist"),
    POST_NOT_FOUND(404, "POST-404-1", "Post Not Found"),

    //Comment
    COMMENT_NOT_FOUND(404, "COMMENT-404-1", "Comment Not Found"),
    RECOMMENT_NOT_FOUND(404, "RECOMMENT-404-1", "ReComment Not Found"),

    //Ber
    Ber_Already_Reserved(400, "BER-400-1", "Ber Already Reserved"),
    Ber_Not_Found(404, "Ber-400-2", "Ber Not Found"),
    Ber_User_Already_Reserved_Same_Time(400, "Ber-400-3", "Ber User Already Reserved Same Time"),
    Ber_Reservation_Time_Before_Now(400, "Ber-400-4", "Ber Reservation Time Before Now"),
    Ber_Reservation_Time_Not_Monday_To_Sunday(400, "BER-400-5", "Ber_Reservation_Time_Not_Sunday_To_Friday "),

    // S3
    IMAGE_FAILED_SAVE(424, "IMAGE-424-1", "Image Failed Save"),
    IMAGE_NOT_FOUND(404, "IMAGE-404-1", "Image Not Found"),

    //JWT
    INVALID_TOKEN(403, "TOKEN-403-1", "Access with Invalid Token"),
    EXPIRED_JWT(403, "TOKEN-403-2", "Access Token Expired"),
    REFRESH_TOKEN_EXPIRED(403, "TOKEN-403-3", "Refresh Token Expired"),

    //Calender
    Invalid_Date(400, "CALENDER-400-1", "Invalid Date"),
    NO_AUTH_TO_DEF_CALENDER(403, "CALENDER-403-1", "No Auth To Def Calender"),
    CALENDER_NOT_FOUND(404, "CALENDER-404-1", "Calender Not Found"),

    //meal
    MEAL_NOT_FOUND(404, "MEAL-404-1", "Meal Not Found"),
    MEAL_TYPE_PARSE(400, "MEAL-401-1", "MEAL TYPE PARSE ERROR"),

    //timeTable
    NO_PERIOD_MATCHED(404, "TIMETABLE-404-1", "TimeTable Not Matches"),
  
    //ServerError
    INVALID_ARGUMENT(400, "ARG-400-1", "Arg Is Not Valid"),
    FORBIDDEN(403, "COMMON-403-1", "Forbidden"),
    NOT_FOUND(404, "NOT_FOUND", "Not Found"),
    BSM_AUTH_INVALID_CLIENT(500, "BSM-500-1", "Bsm Client Is Invalid"),
    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal Server Error"),

    //meister
    MEISTER_INFO_PRIVATE(403, "MEISTER-403-1", "Cannot See Ranking With Setting Private"),
    MEISTER_PASSWORD_FORBIDDEN(403, "MEISTER-403-2", "Meister Password Forbidden"),
    MEISTER_PASSWORD_CHANGED(403, "MEISTER-403-3", "Meister Password Changed"),
    MEISTER_CANNOT_CHANGE_AUTH(403, "MEISTER-403-4", "You Can't Change your Auth"),
    MEISTER_INFO_NOT_FOUND(404, "MEISTER-404-1", "Meister Info Not Found"),
    MEISTER_INTERNAL_SERVER(500, "MEISTER-500-1", "Meister Internal Server Error"),

    //Room
    AlreadyFullRoomMate(400, "Room-400-1", "RoomMate Already Full"),
    Not_ALLOW_DATE(400, "Room-400-2", "Not Allowed Date"),
    NOT_YOUR_ROOM(403, "Room-403-1", "Not your Room"),
    ROOM_NOTFOUND(404, "CheckIn-404-1", "CheckIn Not Found"),

    //CheckIN
    ALREADY_CHECKIN(400, "CheckIn-400-1", "Already checkIn"),
    CHECKIN_NOTFOUND(404, "CheckIn-404-1", "CheckIn Not Found");

    private final int status;
    private final String code;
    private final String message;
}
