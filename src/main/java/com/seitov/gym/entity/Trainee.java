package com.seitov.gym.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "trainee")
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainee_generator")
    @SequenceGenerator(name = "trainee_generator", sequenceName = "trainee_seq")
    private Integer id;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "address")
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="trainer_trainee",
            joinColumns=@JoinColumn(name="trainee_id"),
            inverseJoinColumns=@JoinColumn(name="trainer_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Trainer> trainers;

}
