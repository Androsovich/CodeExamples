package org.androsovich.drivers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.androsovich.drivers.dto.Money;
import org.androsovich.drivers.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PatchMapping("/{id}/money/withdraw")
    @Operation(summary = "Withdraw")
    public void withdraw(@RequestBody Money money, @PathVariable(name = "id") Long accountId) {
        accountService.withdraw(money, accountId);
    }

    @PatchMapping("/{id}/money/deposit")
    @Operation(summary = "Deposit")
    public void deposit(@RequestBody Money money, @PathVariable(name = "id") Long accountId) {
        accountService.deposit(money, accountId);
    }

    @GetMapping("/{id}/money/{currency}")
    @Operation(summary = "Show account balance by id in another currency.")
    public Money showBalanceAccount(@PathVariable(name = "id") Long accountId, @PathVariable(name = "currency") String currency) {
        return accountService.getBalanceByAccountID(currency, accountId);
    }

    @GetMapping("/{id}/money/")
    @Operation(summary = "Show account balance by id.")
    public Money showBalanceAccount(@PathVariable(name = "id") Long accountId) {
        return accountService.getBalanceByAccountID(accountId);
    }
}
