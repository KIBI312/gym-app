package com.seitov.gym.dao;

import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class AbstractJpaDao<T, ID> {

    private EntityManager em;

    private final Class<T> tClass;

    public AbstractJpaDao(Class<T> tClass) {
        this.tClass = tClass;
    }

    protected void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public T findById(ID id) {
        return em.find(tClass, id);
    }

    public List<T> findAll() {
        return em.createQuery("from " + tClass.getName()).getResultList();
    }

    public void create(T toCreate) {
        em.persist(toCreate);
    }

    public T update(T toUpdate) {
        return em.merge(toUpdate);
    }

    public void delete(T toDelete) {
        if(!em.contains(toDelete)) {
            toDelete = em.merge(toDelete);
        }
        em.remove(toDelete);
    }

    public void deleteById(ID id) {
        T toDelete = em.getReference(tClass, id);
        em.remove(toDelete);
    }

}