package com.secondprojinitiumback.common.domain.base;

import com.secondprojinitiumback.common.audit.AuditContextHolder;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    @CreatedBy
    @Column(name = "CRT_ID", length = 10, updatable = false)
    private String createdBy;

    @Column(name = "CRT_PRGRM_ID")
    private Long createdProgramId;

    @CreatedDate
    @Column(name = "CRT_DT", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "CRT_IP", length = 15)
    private String createdIp;

    @LastModifiedBy
    @Column(name = "MOD_ID", length = 10)
    private String modifiedBy;

    @Column(name = "MOD_PRGRM_ID")
    private Long modifiedProgramId;

    @LastModifiedDate
    @Column(name = "MOD_DT")
    private LocalDateTime modifiedDate;

    @Column(name = "MOD_IP", length = 15)
    private String modifiedIp;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.createdIp = AuditContextHolder.getClientIp();
        this.createdProgramId = AuditContextHolder.getProgramId();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
        this.modifiedIp = AuditContextHolder.getClientIp();
        this.modifiedProgramId = AuditContextHolder.getProgramId();
    }
}
