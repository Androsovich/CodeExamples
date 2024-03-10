package org.androsovich.applications.services.security;

import lombok.AllArgsConstructor;
import org.androsovich.applications.dto.auth.AuthenticationRequest;
import org.androsovich.applications.dto.auth.AuthenticationResponse;
import org.androsovich.applications.entities.User;
import org.androsovich.applications.entities.Token;
import org.androsovich.applications.entities.enums.TokenType;
import org.androsovich.applications.repositories.TokenRepository;
import org.androsovich.applications.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.androsovich.applications.constants.Constants.USERNAME_NOT_FOUND_EXCEPTION;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
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
    }
}