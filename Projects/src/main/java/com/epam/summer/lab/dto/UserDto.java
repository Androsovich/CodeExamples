package com.epam.summer.lab.dto;

import com.epam.summer.lab.dto.transfer.New;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

import static com.epam.summer.lab.constants.Constants.USER_DELIMITER_NAME;

@Getter
@Setter
public class UserDto {
    @Null(groups = {New.class})
    private Long id;

    @NotNull(groups = {New.class})
    private String firstName;

    @NotNull(groups = {New.class})
    private String lastName;

    @NotNull(groups = {New.class})
    private String middleName;

    @NotNull(groups = {New.class})
    private LocalDate birthday;

    @NotNull(groups = {New.class})
    @Null
    private String userName;

    @NotNull(groups = {New.class})
    @Null
    private String password;

    @NotNull(groups = {New.class})
    @Email(groups = {New.class})
    private String email;

    @NotNull(groups = {New.class})
    private String[] role;

    public String getFullName() {
        return String.join(USER_DELIMITER_NAME, firstName, lastName, middleName);
    }
}