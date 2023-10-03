package com.seitov.gym.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "specialization_id")
    private TrainingType trainingType;

}
