package org.androsovich.accounts.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.dto.user.UserRequest;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.exceptions.*;
import org.androsovich.accounts.repositories.UserRepository;
import org.androsovich.accounts.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

import static org.androsovich.accounts.constants.Constants.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public Page<User> findAllWithFilteringAndSorting(String filterFirstName, String filterLastName, Pageable pageable) {
        return userRepository.findByFirstNameLikeAndLastNameLike(filterFirstName, filterLastName, pageable);
    }

    @Override
    @Transactional
    public User save(User user) {
        User duplicateCandidate = userRepository.findByEmailOrPhone(user.getEmail(), user.getPhone());

         checkingEmailAndPhoneExists(duplicateCandidate, user.getEmail(), user.getPhone());

        User obtainedUser = userRepository.save(user);
        log.info("method - save: New user - {} , saved successfully.", obtainedUser);
        return obtainedUser;
    }

    @Override
    @Transactional
    public Page<User> findAllByBirthdayGreaterThanEqual(LocalDate birthDay, Pageable pageable) {
        log.info("method - findAllByBirthdayGreaterThanEqual  birthDay - {}, pageable - {}", birthDay, pageable);
        return userRepository.findAllByBirthdayGreaterThanEqual(birthDay, pageable);
    }

    @Override
    @Transactional
    public User findById(@NotNull Long id) {
        log.info("method - findById id - {}", id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ID + id));
    }

    @Override
    @Transactional
    public User findByPhone(@NotNull String phone) {
        log.info("method - findByPhone phone - {}", phone);
        return userRepository.findByPhone(phone).orElseThrow(() -> new UserNotFoundByPhoneException(USER_NOT_FOUND_BY_PHONE + phone));
    }

    @Override
    @Transactional
    public User findByEmail(@NotNull String email) {
        log.info("method - findByEmail email - {}", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundByEmailException(USER_NOT_FOUND_BY_EMAIL + email));
    }

    @Override
    @Transactional
    public User update(UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ID + userRequest.getId()));

        String phone = userRequest.getPhone().isBlank() ? null : userRequest.getPhone();
        String email = userRequest.getEmail().isBlank() ? null : userRequest.getEmail();

        checkingEmailAndPhoneExists(user, email, phone);
        if(Objects.isNull(email) && Objects.isNull(phone)) {

            log.error(BOTH_EMAIL_AND_PHONE_EMPTY);
            throw new IllegalUpdateUserException(BOTH_EMAIL_AND_PHONE_EMPTY);
        }
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

    private void checkingEmailAndPhoneExists(User user, String email, String phone){

        if (Objects.nonNull(user)) {

            if ( Objects.nonNull(email) && email.equals(user.getEmail()) ) {

                log.error(EMAIL_EXISTS_MESSAGE + "{}", user.getEmail());
                throw new UserWithDuplicateEmailException(EMAIL_EXISTS_MESSAGE + user.getEmail());

            } else if( Objects.nonNull(phone) && phone.equals(user.getPhone()) ){

                log.error(PHONE_EXISTS_MESSAGE + "{}", user.getPhone());
                throw new UserWithDuplicatePhoneException(PHONE_EXISTS_MESSAGE + user.getPhone());
            }
        }
    }
}
