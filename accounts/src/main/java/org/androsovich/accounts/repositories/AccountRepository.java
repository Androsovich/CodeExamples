package org.androsovich.accounts.repositories;

import org.androsovich.accounts.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("update Account acc " +
            "set acc.balance = acc.balance * :percentage " +
            "where acc.percentageLimit > acc.balance * :percentage")
    int increaseBalance(@Param("percentage") double percentage);
}
