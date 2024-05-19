package org.androsovich.accounts.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.repositories.UserRepository;
import org.androsovich.accounts.services.UserService;
import org.androsovich.exceptions.UserWithDuplicateEmailException;
import org.androsovich.exceptions.UserWithDuplicatePhoneException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.androsovich.accounts.constants.Constants.EMAIL_EXISTS_MESSAGE;
import static org.androsovich.accounts.constants.Constants.PHONE_EXISTS_MESSAGE;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        User duplicateCandidate = userRepository.findByEmail(user.getEmail());

        if (Objects.nonNull(duplicateCandidate)) {
            throw new UserWithDuplicateEmailException(EMAIL_EXISTS_MESSAGE + user.getEmail());
        }
            duplicateCandidate = userRepository.findByPhone(user.getPhone());

        if (Objects.nonNull(duplicateCandidate)) {
            throw new UserWithDuplicatePhoneException(PHONE_EXISTS_MESSAGE + user.getPhone());
        }
        User obtainedUser = userRepository.save(user);
        log.info("method - save: New user - {} , saved successfully.", user);
        return obtainedUser;
    }
}
