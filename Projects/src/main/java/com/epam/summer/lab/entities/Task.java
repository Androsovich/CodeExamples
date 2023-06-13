package com.epam.summer.lab.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.epam.summer.lab.constants.Constants.MAX_SIZE_TITTLE;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class Task extends BaseEntity {

    @Size(max = MAX_SIZE_TITTLE)
    @Column(name = "title")
    private String title;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User executor;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @Override
    public String toString() {
        return "Task [id= " + getId() +
                ", title= " + title +
                ", startDate= " + startDate +
                ", endDate= " + endDate +
                ", status= " + status +
                "]";
    }
}