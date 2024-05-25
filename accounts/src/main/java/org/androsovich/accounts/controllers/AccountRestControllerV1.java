package org.androsovich.accounts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.androsovich.accounts.dto.OkResponse;
import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.dto.account.TransferMoneyRequest;
import org.androsovich.accounts.dto.assemblers.AccountModelAssembler;
import org.androsovich.accounts.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/accounts", produces = "application/hal+json")
public class AccountRestControllerV1 {
    private final AccountModelAssembler accountModelAssembler;
    private final AccountService accountService;

    @GetMapping("/{id}")
    @Operation(summary = "Get account by id")
    public AccountDto one(@PathVariable Long id) {
        return accountModelAssembler.toModel(accountService.findById(id));
    }

    @PatchMapping("/transfer")
    @Operation(summary = "money transfer to another account. id - id recipient account")
    public ResponseEntity<?> transferMoneyBetweenAccounts(@RequestBody @Valid TransferMoneyRequest transferMoneyRequest) {
        accountService.transferMoneyBetweenAccounts(transferMoneyRequest);
        return ResponseEntity.ok().body(new OkResponse("transfer successfully"));
    }
}
