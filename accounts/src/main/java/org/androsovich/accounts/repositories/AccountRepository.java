package org.androsovich.accounts.repositories;

import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static org.androsovich.accounts.constants.Queries.INCREASE_BALANCE_BY_PERCENTAGE_QUERY;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query(INCREASE_BALANCE_BY_PERCENTAGE_QUERY)
    int increaseBalance(@Param("percentage") double percentage);

    Account findByUser(User user);
}
