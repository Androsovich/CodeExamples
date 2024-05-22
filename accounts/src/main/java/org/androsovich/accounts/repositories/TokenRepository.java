package org.androsovich.accounts.repositories;

import org.androsovich.accounts.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.androsovich.accounts.constants.Queries.FILTER_FIND_ALL_VALID_TOKEN_BY_USER_QUERY;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query(FILTER_FIND_ALL_VALID_TOKEN_BY_USER_QUERY)
    List<Token> findAllValidTokenByUser(Long id);
}
