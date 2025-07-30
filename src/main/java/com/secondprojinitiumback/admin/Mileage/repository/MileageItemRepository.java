package com.secondprojinitiumback.admin.Mileage.repository;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MileageItemRepository extends JpaRepository<MileageItem, Long> {
    // 조건이 있을 때만 검색하고, 없으면 전체 조회 (JPA 쿼리 자동 처리됨)
    @Query("SELECT m FROM MileageItem m WHERE " +
            "(:itemCode IS NULL OR m.itemCode LIKE %:itemCode%) AND " +
            "(:eduNm IS NULL OR m.program.eduNm LIKE %:eduNm%)")
    Page<MileageItem> searchWithPaging(
            @Param("itemCode") String itemCode,
            @Param("eduNm") String eduNm,
            Pageable pageable
    );
}


