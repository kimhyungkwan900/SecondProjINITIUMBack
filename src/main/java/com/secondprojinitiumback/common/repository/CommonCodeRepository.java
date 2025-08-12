package com.secondprojinitiumback.common.repository;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.CommonCodeId;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {
    Optional<CommonCode> findById_CodeAndId_CodeGroup(String id, String semesterCode);

    List<CommonCode> findAllById_CodeGroup(String codeGroup); //목록 리스트로 가져오기
    List<CommonCode> findAllById_CodeGroupAndUseYnOrderBySortOrderAscCodeNameAsc(
            String codeGroup, String useYn
    );
}
