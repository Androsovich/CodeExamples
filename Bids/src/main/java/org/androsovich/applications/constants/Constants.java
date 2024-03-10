package org.androsovich.applications.constants;

public class Constants {

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int JWT_BEGIN_INDEX = 7;
    public static final String USER_NOT_FOUND_BY_NAME = "User Not Found with username: ";
    public static final String NOT_BLANK_MESSAGE_ROLE = "the role should be";
    public static final int MAX_SIZE_NAME_USER = 25;
    public static final int MIN_SIZE_NAME_USER = 2;
    public static final String USERNAME_NOT_FOUND_EXCEPTION = "User not found by username: ";
    public static final String USER_NOT_FOUND_ID = "User not found by id: ";
    public static final String ROLE_NOT_FOUND = "Role not found by name : ";
    public static final String BID_NOT_FOUND_ID = "Bid not found by id: ";
    public static final String AUTH_USER_NOT_FOUND_ID = "User id not found in UserDetails";
    public static final String INVALID_STATUS_MESSAGE = "Invalid status : ";
    public static final String INVALID_ROLE_MESSAGE = "Invalid role";
    public static final String PRIVILEGE_DELIMITER = ":";
    public static final String CREATE = "CREATE";

    public static final String BAD_FEIGN_REQUEST = "Bad request error from Bid service";
    public static final String BAD_FEIGN_RESPONSE = "Bad response : array length must be 1, actual : ";
    public static final String PHONE_MUST_BE_MOBILE = "Phone must be mobile";
    public static final String MOBILE_PHONE_TYPE = "Мобильный";
    public static final String GENERIC_ERROR = "Generic error";

    private Constants() {
    }
}