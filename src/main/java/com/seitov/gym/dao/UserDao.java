package com.seitov.gym.dao;

import com.seitov.gym.entity.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao extends AbstractJpaDao<User, Integer>{

    @PersistenceContext
    private EntityManager em;

    public UserDao() {
        super(User.class);
    }

    @PostConstruct
    private void init() {
        super.setEntityManager(em);
    }

    public List<User> findByUsernameStartingWith(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate usernamePredicate = criteriaBuilder.like(userRoot.get("username"), username+"%");
        criteriaQuery.where(usernamePredicate);
        return em.createQuery(criteriaQuery).getResultList();
    }

    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate usernamePredicate = criteriaBuilder.like(userRoot.get("username"), username);
        criteriaQuery.where(usernamePredicate);
        try {
            return Optional.of(em.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
