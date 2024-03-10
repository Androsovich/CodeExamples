package org.androsovich.applications.entities;

import jakarta.persistence.*;
import lombok.*;
import org.androsovich.applications.entities.embeddeds.Phone;
import org.androsovich.applications.entities.enums.StatusType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="bids")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Bid extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "countryCode", column = @Column(name = "country_code")),
            @AttributeOverride( name = "cityCode", column = @Column(name = "city_code")),
            @AttributeOverride( name = "phone", column = @Column(name = "phone"))
    })
    private Phone phone;

    @CreatedDate
    @Column(name="created_time", updatable = false)
    private LocalDateTime createdTime;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "owner_id", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Override
    public String toString() {
        return "Bid[id=" + getId() +
                ", name=" + name +
                ", status=" + status +
                ", phone=" + phone +
                ", createdTime=" + createdTime +
                ']';
    }
}
