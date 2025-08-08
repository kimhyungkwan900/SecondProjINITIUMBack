package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DscsnKindRepository extends JpaRepository<DscsnKind, String>, DscsnKindRepositoryCustom {
    List<DscsnKind> findByDscsnKindIdStartingWith(String prefix);
}
