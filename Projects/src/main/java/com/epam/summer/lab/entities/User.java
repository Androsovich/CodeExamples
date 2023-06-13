package com.epam.summer.lab.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.epam.summer.lab.constants.Constants.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(name = "first_name")
    @Size(max = MAX_SIZE_NAME_USER, min = MIN_SIZE_NAME_USER)
    private String firstName;

    @Column(name = "last_name")
    @Size(max = MAX_SIZE_NAME_USER, min = MIN_SIZE_NAME_USER)
    private String lastName;

    @Column(name = "middle_name")
    @Size(max = MAX_SIZE_NAME_USER, min = MIN_SIZE_NAME_USER)
    private String middleName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_projects",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")})
    private Set<Project> projects;//

    public String getFullName() {
        return String.join(USER_DELIMITER_NAME, firstName, lastName, middleName);
    }

    @Override
    public String toString() {
        return "User [id= " + getId() +
                ", firstName= " + firstName +
                ", lastName= " + lastName +
                ", middleName= " + middleName +
                ", birthday= " + birthday +
                ", email= " + email +
                "]";
    }
}