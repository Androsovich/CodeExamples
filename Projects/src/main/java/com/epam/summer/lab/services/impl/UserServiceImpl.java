package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.UserException;
import com.epam.summer.lab.repositories.UserRepository;
import com.epam.summer.lab.security.IAuthenticationFacade;
import com.epam.summer.lab.services.UserService;
import com.epam.summer.lab.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.summer.lab.constants.Constants.USERS_NOT_FOUND;
import static com.epam.summer.lab.constants.Constants.USER_NOT_FOUND_BY_NAME;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public User getAuthenticationUser() {
        Authentication authentication = authenticationFacade.getAuthentication();

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String userName = principal.getUsername();
        return
                userRepository
                        .findUserByUserName(userName)
                        .map(user -> {
                            user.getProjects().size();
                            return user;
                        })
                        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_NAME + userName));
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<UserDto> getUsersByProjectId(Long projectId) {
        List<User> users =
                userRepository
                        .findUsersByProjectsId(projectId)
                        .orElseThrow(() -> new UserException(USERS_NOT_FOUND));
        return mapper.toUserDto(users);
    }

    @Override
    public List<UserDto> getUsersNotInProject(Long projectId) {
        List<User> users = userRepository.getUsersNotInProject(projectId);

        return mapper.toUserDto(users);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();

    }
}