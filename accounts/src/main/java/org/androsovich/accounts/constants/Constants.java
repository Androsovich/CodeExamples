package org.androsovich.accounts.constants;

public class Constants {
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final int JWT_BEGIN_INDEX = 7;
    public static final String USER_NOT_FOUND_BY_NAME = "User Not Found with username: ";
    public static final int MAX_SIZE_NAME_USER = 25;
    public static final int MIN_SIZE_NAME_USER = 2;
    public static final String USERNAME_NOT_FOUND_EXCEPTION = "User not found by username: ";
    public static final String USER_NOT_FOUND_ID = "User not found by id: ";
    public static final String AUTH_USER_NOT_FOUND_ID = "User id not found in UserDetails";

    public static final String GENERIC_ERROR = "Generic error";
    public static final String EMAIL_EXISTS_MESSAGE = "There is an user with that email address:";
    public static final String PHONE_EXISTS_MESSAGE = "There is an user with that phone:";

    // 207%
    public static final double LIMIT_PERCENTAGE = 3.07;
    // 5%
    public static final double BALANCE_PERCENTAGE = 1.05;

    public static final String CRON = "1 * * * * *";

    private Constants() {
    }
}
