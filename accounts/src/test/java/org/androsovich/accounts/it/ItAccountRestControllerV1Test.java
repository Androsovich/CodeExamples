package org.androsovich.accounts.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.androsovich.accounts.dto.account.TransferMoneyRequest;
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

import static org.androsovich.accounts.constants.Constants.ACCOUNTS_EQUALS_FOR_TRANSFER;
import static org.androsovich.accounts.util.DataUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItAccountRestControllerV1Test {
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

    private String token;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
        User obtainedUserJohnDoe = userRepository.save(getJohnDoeTransientCryptPassword());
        AuthenticationRequest request = new AuthenticationRequest(obtainedUserJohnDoe.getUsername(), getJohnDoeTransient().getPassword());
        token = authenticationService.authenticate(request).accessToken();
    }
    @Test
    @DisplayName("IT Test the controller when one (find by id) : success")
    @Transactional
    void givenValidIdThenSuccessResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/accounts/" + accountMikeWong.getId())
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", CoreMatchers.is(100)));
    }

    @Test
    @DisplayName("IT Test the method of getting the account by incorrect id")
    @Transactional
    void givenIncorrectIdWhenGetByIdThenErrorResponse() throws Exception {
        //given
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/accounts/100")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Account not found by id: 100")));
    }

    @Test
    @DisplayName("IT Test transferMoneyBetweenAccounts response - success functionality")
    @Transactional
    void givenTransferMoneyRequestWhenTransferMoneyThenResponseSuccess() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        User obtainedPetrSnow = userRepository.save(getPetrSnowTransient());
        Account accountPetrSnow = accountRepository.save(
                new Account(null, obtainedPetrSnow, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(accountMikeWong.getId(), accountPetrSnow.getId(), BigDecimal.valueOf(50));
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/accounts/transfer")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(transferMoneyRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("transfer successfully")));
    }

    @Test
    @DisplayName("IT Test transferMoneyBetweenAccounts with equal accounts id functionality")
    @Transactional
    void givenTransferMoneyRequestWithEqualsAccountIdWhenTransferMoneyThenErrorResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(accountMikeWong.getId(), accountMikeWong.getId(), BigDecimal.valueOf(50));
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/accounts/transfer")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(transferMoneyRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is(ACCOUNTS_EQUALS_FOR_TRANSFER)));
    }

    @Test
    @DisplayName("IT Test transferMoneyBetweenAccounts with negative balance from request functionality")
    @Transactional
    void givenTransferMoneyRequestWithNegativeBalanceWhenTransferMoneyThenErrorResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        User obtainedPetrSnow = userRepository.save(getPetrSnowTransient());
        Account accountPetrSnow = accountRepository.save(
                new Account(null, obtainedPetrSnow, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(accountMikeWong.getId(), accountMikeWong.getId(), BigDecimal.valueOf(-50));
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/accounts/transfer")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(transferMoneyRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(model().attribute("balance", "must be greater than or equal to 0"));
    }

    @Test
    @DisplayName("IT Test transferMoneyBetweenAccounts with invalid fromAccount id functionality")
    @Transactional
    void givenTransferMoneyRequestWithInvalidFromAccountIdWhenTransferMoneyThenErrorResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(-100, accountMikeWong.getId(), BigDecimal.valueOf(100));
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/accounts/transfer")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(transferMoneyRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Account not found by id: " + (-100))));
    }

    @Test
    @DisplayName("IT Test transferMoneyBetweenAccounts with invalid toAccount id functionality")
    @Transactional
    void givenTransferMoneyRequestWithInvalidToAccountIdWhenTransferMoneyThenErrorResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(accountMikeWong.getId(), -200 , BigDecimal.valueOf(100));
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/accounts/transfer")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(transferMoneyRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Account not found by id: " + (-200))));
    }

    @Test
    @DisplayName("IT Test transferMoneyBetweenAccounts with balance higher then from account balance functionality")
    @Transactional
    void givenTransferMoneyRequestWithBalanceHigherThenFromAccountBalanceWhenTransferMoneyThenErrorResponse() throws Exception {
        //given
        User obtainedMikeWong = userRepository.save(getMikeWongTransient());
        Account accountMikeWong = accountRepository.save(
                new Account(null, obtainedMikeWong, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));

        User obtainedPetrSnow = userRepository.save(getPetrSnowTransient());
        Account accountPetrSnow = accountRepository.save(
                new Account(null, obtainedPetrSnow, BigDecimal.valueOf(100), BigDecimal.valueOf(307)));
        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest(accountMikeWong.getId(), accountPetrSnow.getId(), BigDecimal.valueOf(150));
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/accounts/transfer")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(transferMoneyRequest))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        CoreMatchers.is("Not enough money : " + 100 +  " : withdraw  " + 150)));
    }
}