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

    public Long getNextApplySequence() {
        return ((Number) em.createNativeQuery("SELECT NEXTVAL(apply_seq)").getSingleResult()).longValue();
    }
}

/*
sql문에 아래 코드 추가, MySQL 시퀀스 생성하는 코드

CREATE SEQUENCE schedule_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE apply_seq
    START WITH 1
    INCREMENT BY 1;
 */
