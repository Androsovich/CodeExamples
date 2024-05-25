package org.androsovich.accounts.it;

import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.repositories.UserRepository;
import org.androsovich.accounts.services.SchedulerService;
import org.androsovich.accounts.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ItSchedulerServiceImplTest {

    @Autowired
    SchedulerService schedulerService;

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
    @Transactional
    void givenSleepBy100ms_whenIncreaseBalanceAccount_thenIsGreaterThanZero()
            throws InterruptedException {
        //given
        Thread.sleep(100L);
        User obtainedUser = userRepository.save(DataUtils.getJohnDoeTransient());
        accountRepository.save(new Account(null, obtainedUser, new BigDecimal(100), new BigDecimal(300)));

        //when
        int updateAccountsNumber = schedulerService.increaseBalanceAccount();

        //then
        assertThat(updateAccountsNumber).isGreaterThan(0);
    }

    @Test
    @Transactional
    void givenSleepBy100msAndAccountLimitEqualBalance_whenIncreaseBalanceAccount_thenIsZero()
            throws InterruptedException {
        //given
        Thread.sleep(100L);
        User obtainedUser = userRepository.save(DataUtils.getJohnDoeTransient());
        accountRepository.save(new Account(null, obtainedUser, new BigDecimal(300), new BigDecimal(300)));

        //when
        int updateAccountsNumber = schedulerService.increaseBalanceAccount();

        //then
        assertThat(updateAccountsNumber).isZero();
    }
}