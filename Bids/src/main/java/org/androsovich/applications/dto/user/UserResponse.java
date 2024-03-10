package org.androsovich.applications.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.androsovich.applications.dto.role.RoleResponse;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse extends RepresentationModel<UserResponse> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleResponse> roles;


}
