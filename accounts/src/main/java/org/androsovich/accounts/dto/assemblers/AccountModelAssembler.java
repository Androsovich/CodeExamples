package org.androsovich.accounts.dto.assemblers;

import jakarta.validation.constraints.NotNull;
import org.androsovich.accounts.controllers.AccountRestControllerV1;
import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.entities.Account;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, AccountDto> {

    @Override
    public AccountDto toModel(@NotNull Account account) {
        return getAccountDtoFromAccount(account, new ModelMapper());
    }

    @Override
    public CollectionModel<AccountDto> toCollectionModel(@NotNull Iterable<? extends Account> accounts) {
        ModelMapper modelMapper = new ModelMapper();
        List<AccountDto> accountDtos = new ArrayList<>();

        for (Account account : accounts) {
            AccountDto accountDto = getAccountDtoFromAccount(account, modelMapper);
            accountDtos.add(accountDto);
        }
        return CollectionModel.of(accountDtos);
    }

    private AccountDto getAccountDtoFromAccount(Account account, @NonNull ModelMapper mapper) {
        AccountDto accountDto = mapper.map(account, AccountDto.class);
        accountDto.add(linkTo(AccountRestControllerV1.class).withRel("/v1/accounts"));
        accountDto.add(linkTo(methodOn(AccountRestControllerV1.class).one(account.getId())).withSelfRel());
        return accountDto;
    }
}

