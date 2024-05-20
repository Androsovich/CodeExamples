package org.androsovich.accounts.controllers.auth;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.androsovich.accounts.dto.auth.AuthenticationRequest;
import org.androsovich.accounts.dto.auth.AuthenticationResponse;
import org.androsovich.accounts.services.auth.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/logout")
    @Operation(summary = "Logout. The JWT token will be blacklisted")
    public HttpStatus logout() {
        return HttpStatus.OK;
    }
}
