package com.seitov.gym.dao;

import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Optional;

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

    public Optional<Trainee> findByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = cb.createQuery(Trainee.class);
        Root<Trainee> traineeRoot = criteriaQuery.from(Trainee.class);
        Join<Trainee, User> joinUser = traineeRoot.join("user");
        Predicate usernamePredicate = cb.equal(joinUser.get("username"), username);
        criteriaQuery.where(usernamePredicate);
        try {
            return Optional.of(em.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
    
}
