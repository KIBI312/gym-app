package com.seitov.gym.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "training_type")
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name", updatable = false, insertable = false)
    private Name name;

    public enum Name {
        fitness, yoga, zumba, stretching, resistance
    }

}
