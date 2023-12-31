package org.androsovich.drivers.repositories;

import org.androsovich.drivers.entities.Account;
import org.androsovich.drivers.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("update Account acc set acc.balance = :balance where acc.id = :id")
    int updateAccountBalance(@Param("balance") BigDecimal balance, @Param("id") Long accountId);


}
