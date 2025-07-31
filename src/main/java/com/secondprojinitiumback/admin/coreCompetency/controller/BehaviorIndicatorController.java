package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.service.BehaviorIndicatorService;
import com.secondprojinitiumback.user.coreCompetency.dto.BehaviorIndicatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 행동지표(BehaviorIndicator) 관련 API 컨트롤러
 * - 공통 여부 및 전공 코드에 따라 행동지표 리스트를 조회
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/behavior-indicators")
public class BehaviorIndicatorController {

    private final BehaviorIndicatorService behaviorIndicatorService;

    /**
     * 행동지표 목록 조회
     *
     * @param isCommon "Y"면 공통, "N"이면 전공별
     * @param subjectCode 전공 코드 (공통일 경우 null 가능)
     * @return 해당 조건에 따른 행동지표 리스트
     */
    @GetMapping
    public ResponseEntity<List<BehaviorIndicatorDto>> getIndicators(
            @RequestParam(name = "isCommon") String isCommon,
            @RequestParam(name = "subjectCode", required = false) String subjectCode) {

        // 공통 여부를 boolean 값으로 변환
        boolean commonFlag = isCommon.equalsIgnoreCase("Y");

        // 서비스에서 조건에 맞는 행동지표 조회
        List<BehaviorIndicatorDto> indicators = behaviorIndicatorService.getIndicatorsBySubject(subjectCode, commonFlag);

        return ResponseEntity.ok(indicators);
    }
}
