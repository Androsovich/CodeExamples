package org.androsovich.accounts.constants;

public class Queries {
    private Queries() {}

    public static final String FILTER_USERS_ON_FIRST_NAME_AND_LAST_NAME_QUERY = """
            select b from User b \s
            where UPPER(b.firstName) like CONCAT(UPPER(?1),'%') \s
            and UPPER(b.lastName) like CONCAT(UPPER(?2),'%')\s
            """;

    public static final String FILTER_FIND_ALL_VALID_TOKEN_BY_USER_QUERY = """
            select token from Token token inner join User user \s
            on token.user.id = user.id \s
            where user.id = :id and (token.expired = false or token.revoked = false)\s
            """;
    public static final String INCREASE_BALANCE_BY_PERCENTAGE_QUERY = """
            update Account acc \s
            set acc.balance = acc.balance * :percentage \s
            where acc.percentageLimit > acc.balance * :percentage
            """;
    
}
