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
    public static final String USER_NOT_FOUND_BY_PHONE = "User not found by phone: ";
    public static final String USER_NOT_FOUND_BY_EMAIL = "User not found by email: ";
    public static final String ACCOUNT_NOT_FOUND_BY_ID = "Account not found by id: ";
    public static final String ACCOUNT_NOT_FOUND_ENOUGH_MONEY = "Not enough money : ";
    public static final String ACCOUNTS_EQUALS_FOR_TRANSFER = "Accounts equals for transfer";
    public static final String BALANCE_MUST_BE_NOT_NEGATIVE = "The balance should not be negative";

    public static final String EMAIL_EXISTS_MESSAGE = "There is an user with that email address:";
    public static final String PHONE_EXISTS_MESSAGE = "There is an user with that phone:";
    public static final String ACCOUNT_EXISTS_MESSAGE = "There is an account with that user :";
    public static final String BOTH_EMAIL_AND_PHONE_EMPTY = "email or phone must be not null";

    // 207%
    public static final double LIMIT_PERCENTAGE = 3.07;
    // 5%
    public static final double BALANCE_PERCENTAGE = 1.05;

    public static final String CRON = "1 * * * * *";


    private Constants() {
    }
}
