package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DscsnInfoRepositoryCustom {
    Page<DscsnInfo> getDscsnInfoPageByCondition(DscsnInfoSearchDto dscsnInfoSearchDto, Pageable pageable);
}
