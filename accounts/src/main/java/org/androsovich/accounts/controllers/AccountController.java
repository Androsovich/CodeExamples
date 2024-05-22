package org.androsovich.accounts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.dto.assemblers.AccountModelAssembler;
import org.androsovich.accounts.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/accounts", produces = "application/hal+json")
public class AccountController {
    private final AccountModelAssembler accountModelAssembler;
    private final AccountService accountService;

    @GetMapping("/{id}")
    @Operation(summary = "")
    public AccountDto one(@PathVariable Long id) {
        return accountModelAssembler.toModel(accountService.findById(id));
    }

    @PatchMapping("/transfer/{id}")
    @Operation(summary = "money transfer to another account. id - id recipient account")
    public ResponseEntity<?> transferMoneyBetweenAccounts(@RequestBody @Valid AccountDto accountDto, @PathVariable Long recipientAccountId) {
        accountService.transferMoneyBetweenAccounts(accountDto, recipientAccountId);
        return ResponseEntity.ok("transfer successfully");
    }
}
