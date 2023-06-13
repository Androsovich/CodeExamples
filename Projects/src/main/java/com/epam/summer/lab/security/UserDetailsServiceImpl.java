package com.epam.summer.lab.security;

import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.epam.summer.lab.constants.Constants.USER_NOT_FOUND_BY_NAME;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUserName(name);
        user.orElseThrow(()->new UsernameNotFoundException(USER_NOT_FOUND_BY_NAME + name) );
        return user.map(UserDetailsImpl::new).get();
    }
}