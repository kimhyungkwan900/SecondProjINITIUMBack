package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// 문항 등록/수정 요청을 위한 DTO
public class CoreCompetencyQuestionCreateDto {
    private Long Id;                // 문항 ID
    private Long assessmentId;       // 진단평가 ID
    private Integer questionNo;      // 문항 번호
    private String questionName;     // 문항 내용
    private String questionContent;  // 문항 설명
    private Integer displayOrder;    // 화면 출력 순서
    private Integer answerAllowCount;// 허용 응답 개수
    private String subjectCode; // SCSBJT_NO, 전공별 문항일 경우에만 사용
    private Long indicatorId;       // 행동지표 ID (공통문항 아닐 경우 필수)
    private String isCommonCode;        // 공통문항 여부

    //엔티티 -> dto 변환 메소드
    public static CoreCompetencyQuestionCreateDto fromEntity(CoreCompetencyQuestion question) {
        return CoreCompetencyQuestionCreateDto.builder()
                .Id(question.getId())
                .assessmentId(question.getAssessment().getId())
                .questionNo(question.getQuestionNo())
                .questionName(question.getName())
                .questionContent(question.getDescription())
                .displayOrder(question.getDisplayOrder())
                .answerAllowCount(question.getAnswerAllowCount())
                .indicatorId(question.getBehaviorIndicator() != null ? question.getBehaviorIndicator().getId() : null)
                .isCommonCode(question.getBehaviorIndicator() != null ? "N" : "Y")
                .build();
    }
}
