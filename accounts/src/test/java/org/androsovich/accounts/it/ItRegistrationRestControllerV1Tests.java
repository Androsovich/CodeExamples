package org.androsovich.accounts.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.androsovich.accounts.dto.RegistrationUserRequest;
import org.androsovich.accounts.dto.auth.AuthenticationRequest;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.repositories.UserRepository;
import org.androsovich.accounts.services.auth.AuthenticationService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.androsovich.accounts.util.DataUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItRegistrationRestControllerV1Tests {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("IT Test registration new user with him account : success")
    void givenRegistrationUserRequestWhenRegistrationThenSuccessResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("werty")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("test@test.test")
                .phone("202 555 4444")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                        .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Den")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Deny")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday", CoreMatchers.is("1995-12-12")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is("test@test.test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", CoreMatchers.is("202 555 4444")));
    }

    @Test
    @DisplayName("IT Test registration new user with exists username - error response")
    @Transactional
    void givenRegistrationUserRequestWithExistsUserNameWhenRegistrationThenErrorResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("test@test.test")
                .phone("202 555 4444")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("IT Test registration new user with current birthday - error response")
    @Transactional
    void givenRegistrationUserRequestWithCurrentBirthdayWhenRegistrationThenErrorResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.now())
                .email("test@test.test")
                .phone("202 555 4444")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("birthday", "Please provide a birthday"));
    }


    @Test
    @DisplayName("IT Test registration new user with invalid birthday - error response")
    @Transactional
    void givenRegistrationUserRequestWithInvalidBirthdayWhenRegistrationThenErrorResponse() throws Exception {
        //given
        String request = "{\"firstName\":\"Den\",\"lastName\":\"Deny\",\"birthday\":\"2024-rtr\",\"username\":" +
                "\"www1\",\"password\":\"werty\",\"phone\":\"202 555 4444\",\"email\":\"test@test.test\",\"balance\":100.0}";
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("IT Test registration new user with empty birthday - error response")
    @Transactional
    void givenRegistrationUserRequestWithEmptyBirthdayWhenRegistrationThenErrorResponse() throws Exception {
        //given
        String request = "{\"firstName\":\"Den\",\"lastName\":\"Deny\",\"birthday\":\"\",\"username\":" +
                "\"www1\",\"password\":\"werty\",\"phone\":\"202 555 4444\",\"email\":\"test@test.test\",\"balance\":100.0}";
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("birthday", "must not be null"));
    }

    @Test
    @DisplayName("IT Test registration new user with invalid email - error response")
    @Transactional
    void givenRegistrationUserRequestWithInvalidEmailWhenRegistrationThenErrorResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("testtest")
                .phone("202 555 4444")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("email", "Please provide a valid email address"));
    }

    @Test
    @DisplayName("IT Test registration new user with empty email - error response")
    @Transactional
    void givenRegistrationUserRequestWithEmptyEmailWhenRegistrationThenErrorResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("")
                .phone("202 555 4444")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("email", "email address must be not empty"));
    }

    @Test
    @DisplayName("IT Test registration new user with empty phone - error response")
    @Transactional
    void givenRegistrationUserRequestWithEmptyPhoneWhenRegistrationThenErrorResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("qqw@aa.ss")
                .phone("")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("phone", "must not be blank"));
    }

    @Test
    @DisplayName("IT Test registration new user with invalid phone - error response")
    @Transactional
    void givenRegistrationUserRequestWithInvalidPhoneWhenRegistrationThenErrorResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("qqw@aa.ss")
                .phone("wqw22343432")
                .balance(100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("phone", "Invalid phone number"));
    }

    @Test
    @DisplayName("IT Test registration new user with negative balance - error response")
    @Transactional
    void givenRegistrationUserRequestWithNegativeBalanceWhenRegistrationThenErrorResponse() throws Exception {
        //given
        RegistrationUserRequest request = RegistrationUserRequest.builder()
                .firstName("Den")
                .lastName("Deny")
                .username("www1")
                .password("werty")
                .birthday(LocalDate.of(1995, 12, 12))
                .email("qqw@aa.ss")
                .phone("202 555 4444")
                .balance(-100d)
                .build();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/registration")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("balance", "must be greater than or equal to 0"));
    }
}