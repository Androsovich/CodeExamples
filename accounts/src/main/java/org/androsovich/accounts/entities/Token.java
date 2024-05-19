package org.androsovich.accounts.entities;

import jakarta.persistence.*;
import lombok.*;
import org.androsovich.accounts.entities.enums.TokenType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tokens")
public class Token extends BaseEntity {

    @Column(name="token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name="token_type")
    private TokenType tokenType;

    @Column(name="expired")
    private boolean expired;

    @Column(name="revoked")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
