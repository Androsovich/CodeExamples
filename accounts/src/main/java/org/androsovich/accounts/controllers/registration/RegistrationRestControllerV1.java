package org.androsovich.accounts.controllers.registration;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.dto.ErrorResponse;
import org.androsovich.accounts.dto.RegistrationUserRequest;
import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.dto.assemblers.RegistrationUserEntityAssembler;
import org.androsovich.accounts.dto.assemblers.UserModelAssembler;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.services.AccountService;
import org.androsovich.accounts.services.UserService;
import org.androsovich.accounts.exceptions.UserWithDuplicateEmailException;
import org.androsovich.accounts.exceptions.UserWithDuplicatePhoneException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationRestControllerV1 {
    private final UserService userService;
    private final AccountService accountService;
    private final RegistrationUserEntityAssembler userEntityAssembler;
    private final UserModelAssembler userModelAssembler;

    @PostMapping
    @Operation(summary = "Adding a new user to the banking system with an account balance")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationUserRequest request) {
        try {
            User user = userEntityAssembler.toEntity(request);
            User obtanedUser = userService.save(user);

            Account account = AccountDto.toEntity(obtanedUser, request.getBalance());
            accountService.save(account);
            return ResponseEntity.ok(userModelAssembler.toModel(obtanedUser));

        } catch (UserWithDuplicateEmailException | UserWithDuplicatePhoneException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder()
                            .status(400)
                            .message(e.getMessage())
                            .build());
        }
    }
}