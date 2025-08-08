package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosisSubmitRequestDto {

    /**
     * 학생 고유 식별 번호 (STDNT_NO 컬럼 매핑 예상)
     * 기존에는 userId(Long) 사용했지만, 문자열 형태의 studentNo로 변경됨
     * 예: "20251234"
     */
    private String studentNo; // 진단검사 응답을 제출하는 학생의 번호

    /**
     * 진단검사 식별 ID
     * DB의 DGNSTC_TST 테이블의 PK 값
     */
    private Long testId; // 어떤 진단검사에 대한 응답인지 구분하기 위한 ID

    /**
     * 사용자가 선택한 답변 목록
     * 내부 클래스 AnswerSubmission 리스트로 구성됨
     */
    private List<AnswerSubmission> answers; // 문항별 응답 데이터 모음

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerSubmission {
        /**
         * 질문 ID (DGNSTC_QSTN 테이블의 PK 값)
         */
        private Long questionId;// 어떤 문항에 대한 응답인지 식별

        /**
         * 사용자가 선택한 보기 값
         * Likert 척도나 YES/NO 등의 선택 값 저장
         */
        private Integer selectedValue;// 선택한 보기(점수) 값
    }
}
