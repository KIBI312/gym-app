package com.seitov.gym.dao;

import com.seitov.gym.entity.Training;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TrainingDao extends AbstractJpaDao<Training, Integer> {

    @PersistenceContext
    private EntityManager em;

    public TrainingDao() {
        super(Training.class);
    }

    @PostConstruct
    private void init() {
        super.setEntityManager(em);
    }

}
