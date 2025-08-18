package com.secondprojinitiumback.common.security.Repository;

import com.secondprojinitiumback.common.security.domain.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    @Query("SELECT ph FROM PasswordHistory ph WHERE ph.loginId = :loginId ORDER BY ph.changeDateTime DESC")
    List<PasswordHistory> findByLoginIdOrderByChangeDateTimeDesc(@Param("loginId") String loginId);

    @Query("SELECT ph FROM PasswordHistory ph WHERE ph.loginId = :loginId ORDER BY ph.changeDateTime DESC LIMIT :limit")
    List<PasswordHistory> findRecentPasswordHistory(@Param("loginId") String loginId, @Param("limit") int limit);

    @Modifying
    @Query("DELETE FROM PasswordHistory ph WHERE ph.loginId = :loginId AND ph.passwordHistoryId NOT IN " +
           "(SELECT ph2.passwordHistoryId FROM PasswordHistory ph2 WHERE ph2.loginId = :loginId " +
           "ORDER BY ph2.changeDateTime DESC LIMIT :keepCount)")
    void deleteOldPasswordHistory(@Param("loginId") String loginId, @Param("keepCount") int keepCount);

    @Query("SELECT COUNT(ph) FROM PasswordHistory ph WHERE ph.loginId = :loginId")
    long countByLoginId(@Param("loginId") String loginId);
}