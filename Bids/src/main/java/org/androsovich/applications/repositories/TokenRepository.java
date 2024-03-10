package org.androsovich.applications.repositories;

import org.androsovich.applications.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    @Query(value = """
      select token from Token token inner join User user\s
      on token.user.id = user.id\s
      where user.id = :id and (token.expired = false or token.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);
}
