package org.androsovich.accounts.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.androsovich.accounts.constants.Constants.MAX_SIZE_NAME_USER;
import static org.androsovich.accounts.constants.Constants.MIN_SIZE_NAME_USER;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

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
    @NotNull
    @Past
    private LocalDate birthday;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User [id= " + getId() +
                ", firstName= " + firstName +
                ", lastName= " + lastName +
                ", middleName= " + middleName +
                ", email= " + email +
                "]";
    }
}
