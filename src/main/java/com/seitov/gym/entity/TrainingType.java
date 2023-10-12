package com.seitov.gym.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "training_type")
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_type_generator")
    @SequenceGenerator(name = "training_type_generator", sequenceName = "training_type_seq")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Name name;

    public enum Name {
        fitness, yoga, zumba, stretching, resistance
    }

}
