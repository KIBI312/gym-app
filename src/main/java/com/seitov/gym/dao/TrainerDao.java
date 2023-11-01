package com.seitov.gym.dao;

import com.seitov.gym.entity.Trainee;
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
import java.util.Optional;
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

    public Optional<Trainer> findByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = cb.createQuery(Trainer.class);
        Root<Trainer> traineeRoot = criteriaQuery.from(Trainer.class);
        Join<Trainer, User> joinUser = traineeRoot.join("user");
        Predicate usernamePredicate = cb.equal(joinUser.get("username"), username);
        criteriaQuery.where(usernamePredicate);
        try {
            return Optional.of(em.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Trainer> findWithoutTrainee(Trainee traineeToExclude) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = cb.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = criteriaQuery.from(Trainer.class);
        Subquery<Trainer> subquery = criteriaQuery.subquery(Trainer.class);
        Root<Trainer> subqueryRoot = subquery.from(Trainer.class);
        Join<Trainer, User> userJoin = trainerRoot.join("user");
        subquery.select(subqueryRoot);
        subquery.where(cb.isMember(traineeToExclude, subqueryRoot.get("trainees")));
        Predicate notContainsTrainee = cb.not(trainerRoot.in(subquery));
        Predicate isActivePredicate = cb.isTrue(userJoin.get("isActive"));
        Predicate finalPredicate = cb.and(notContainsTrainee, isActivePredicate);
        criteriaQuery.select(trainerRoot).where(finalPredicate);
        return em.createQuery(criteriaQuery).getResultList();
    }

}
