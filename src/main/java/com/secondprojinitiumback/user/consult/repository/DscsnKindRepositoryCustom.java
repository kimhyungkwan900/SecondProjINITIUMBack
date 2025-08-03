package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DscsnKindRepositoryCustom {
    Page<DscsnKind> getDscsnKindPageByCondition(DscsnKindDto dscsnKindDto, Pageable pageable);
}
