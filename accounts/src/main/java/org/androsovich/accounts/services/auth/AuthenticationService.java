package org.androsovich.accounts.services.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.dto.auth.AuthenticationRequest;
import org.androsovich.accounts.dto.auth.AuthenticationResponse;
import org.androsovich.accounts.entities.Token;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.entities.enums.TokenType;
import org.androsovich.accounts.repositories.TokenRepository;
import org.androsovich.accounts.repositories.UserRepository;
import org.androsovich.accounts.security.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.androsovich.accounts.constants.Constants.USERNAME_NOT_FOUND_EXCEPTION;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.name(),
                        request.password()
                )
        );

        var user = repository.findByUserName(request.name())
                .orElseThrow(()-> new UsernameNotFoundException(USERNAME_NOT_FOUND_EXCEPTION + request.name()));

        var jwtToken = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        log.info("|AuthenticationService::authenticate| Authenticate successfully.jwtToken - {} ", jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
        log.info("|AuthenticationService::authenticate| save token for login : - {} successfully.",  user.getUsername());
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
        log.info("|AuthenticationService::authenticate|  revoke all User tokens for login: - {} .",  user.getUsername());
    }
}
