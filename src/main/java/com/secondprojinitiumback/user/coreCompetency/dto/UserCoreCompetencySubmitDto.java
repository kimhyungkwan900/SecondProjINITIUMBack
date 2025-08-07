package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

import java.util.List;

/**
 * 📦 사용자 핵심역량 진단 응답 제출용 DTO
 *
 * 프론트엔드에서 학생이 진단 평가를 완료하고 서버에 제출할 때 사용됨.
 * 평가 ID와 각 문항에 대한 응답 리스트를 포함함.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCoreCompetencySubmitDto {

    /** 진단 평가 ID */
    private Long assessmentId;

    /** 문항별 응답 리스트 */
    private List<ResponseItem> responseItemList;

    /**
     * 📄 단일 문항 응답 정보 클래스
     *
     * 하나의 문항에 대해 어떤 선택지를 골랐는지 응답 정보를 나타냄
     */
    @Getter
    @Setter
    public static class ResponseItem {
        /** 문항 ID */
        private Long questionId;

        /** 선택한 보기의 라벨 (예: '매우 그렇다') */
        private String label;

        /** 해당 선택지에 부여된 점수 */
        private int score;
    }
}
