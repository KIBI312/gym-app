package com.seitov.gym.dao;

import com.seitov.gym.entity.Trainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TrainerDao extends AbstractJpaDao<Trainer, Integer> {

    @PersistenceContext
    private EntityManager em;

    public TrainerDao() {
        super(Trainer.class);
    }

    @PostConstruct
    private void init() {
        super.setEntityManager(em);
    }

}
