package com.seitov.gym.dao;

import com.seitov.gym.entity.Trainer;
import com.seitov.gym.entity.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public Set<Trainer> findByUsername(List<String> usernames) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = cb.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = criteriaQuery.from(Trainer.class);
        Join<Trainer, User> joinUser = trainerRoot.join("user");
        Set<Trainer> trainers = new HashSet<>();
        usernames.forEach(username -> {
            try {
                Predicate usernamePredicate = cb.equal(joinUser.get("username"), username);
                criteriaQuery.where(usernamePredicate);
                trainers.add(em.createQuery(criteriaQuery).getSingleResult());
            } catch (NoResultException ex) {
                //Ignore non-existing usernames
            }
        });
        return trainers;
    }

}
