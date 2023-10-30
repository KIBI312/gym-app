package com.seitov.gym.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_generator")
    @SequenceGenerator(name = "trainer_generator", sequenceName = "trainer_seq")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "specialization_id")
    private TrainingType trainingType;
    @ManyToMany(mappedBy = "trainers", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Trainee> trainees;

}
