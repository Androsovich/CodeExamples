package org.androsovich.accounts.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Min(0)
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Min(0)
    @Column(name = "percentage_limit", nullable = false)
    private BigDecimal percentageLimit = BigDecimal.ZERO;

    @Override
    public String toString() {
        return "Account[" +
                "id=" + id +
                ", user_id =" + user.getId() +
                ", balance=" + balance +
                ", percentageLimit=" + percentageLimit +
                ']';
    }
}
