package org.androsovich.accounts.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AuthenticationResponse(@JsonProperty("access_token") String accessToken) {
}
