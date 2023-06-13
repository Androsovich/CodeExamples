package com.epam.summer.lab.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

import static com.epam.summer.lab.constants.Constants.NOT_BLANK_MESSAGE_ROLE;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {
    @Column(name = "name")
    @NotBlank(message = NOT_BLANK_MESSAGE_ROLE)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public String toString() {
        return "Role [id= " + getId() +
                ", name= " + name +
                "]";
    }
}