package com.seitov.gym.dao;

import com.seitov.gym.entity.TrainingType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class TrainingTypeDao {

    @PersistenceContext
    private EntityManager em;

    public TrainingType findByName(TrainingType.Name name) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
        Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
        criteriaQuery.where(namePredicate);
        return em.createQuery(criteriaQuery).getSingleResult();
    }


}
