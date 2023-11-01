package com.seitov.gym.entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_generator")
    @SequenceGenerator(name = "training_generator", sequenceName = "training_seq")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "training_date")
    private LocalDate trainingDate;
    @Column(name = "duration")
    private Number duration;
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_type_id")
    private TrainingType type;

}
