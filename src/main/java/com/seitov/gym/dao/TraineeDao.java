package com.seitov.gym.dao;

import com.seitov.gym.entity.Trainee;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TraineeDao extends AbstractJpaDao<Trainee, Integer> {

    @PersistenceContext
    private EntityManager em;

    public TraineeDao() {
        super(Trainee.class);
    }

    @PostConstruct
    private void init() {
        super.setEntityManager(em);
    }
    
}
