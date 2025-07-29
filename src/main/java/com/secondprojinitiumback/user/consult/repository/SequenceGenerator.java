package com.secondprojinitiumback.user.consult.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceGenerator {

    @PersistenceContext
    private EntityManager em;

    public Long getNextScheduleSequence() {
        return ((Number) em.createNativeQuery("SELECT NEXTVAL(schedule_seq)").getSingleResult()).longValue();
    }
}
