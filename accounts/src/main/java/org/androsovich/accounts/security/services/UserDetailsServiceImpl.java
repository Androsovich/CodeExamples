package org.androsovich.accounts.security.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.androsovich.accounts.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.androsovich.accounts.constants.Constants.USER_NOT_FOUND_BY_NAME;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_NAME + username));
    }
}
