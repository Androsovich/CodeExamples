package com.epam.summer.lab.constants;

public class SqlQueries {
    public final static String SQL_GET_USERS_NOT_IN_PROJECT = "SELECT * FROM users " +
            "WHERE id NOT IN (SELECT user_id FROM user_projects u_p WHERE u_p.project_id = :project_id )";

    public final static String SQL_SAVE_USER_TO_PROJECT =
            " INSERT INTO user_projects (user_id, project_id) values(:user_id, :project_id)";
}