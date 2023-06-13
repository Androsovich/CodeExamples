package com.epam.summer.lab.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

import static com.epam.summer.lab.constants.Constants.MAX_SIZE_TITTLE;

@Entity
@Table(name = "projects")
@Setter
@Getter
@NoArgsConstructor
public class Project extends BaseEntity {

    @Size(max = MAX_SIZE_TITTLE)
    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_projects",
            joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> projectUsers;

    @Override
    public String toString() {
        return "Project [id= " + getId() +
                ", title= " + title +
                ", status= " + status +
                "]";
    }
}