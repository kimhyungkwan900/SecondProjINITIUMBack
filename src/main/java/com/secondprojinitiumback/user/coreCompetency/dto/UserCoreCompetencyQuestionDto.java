package com.secondprojinitiumback.user.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.dto.ResponseChoiceOptionDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCoreCompetencyQuestionDto {

    // 문항 ID (PK)
    private Long questionId;

    // 문항 번호 (예: 1번, 2번)
    private Integer questionNo;

    // 문항 내용 (예: "나는 계획을 세우고 실천하는 것을 잘한다.")
    private String questionName;

    // 문항에 연결된 선택지 리스트 (보기 항목들)
    List<ResponseChoiceOptionDto> responseChoiceOptions;

    //문항이 속해있는 진단 검사
    private Long assessmentId;

    /**
     * CoreCompetencyQuestion 엔티티를 UserCoreCompetencyQuestionDto로 변환
     * - 선택지(responseChoiceOptions)도 DTO로 매핑
     */
    public static UserCoreCompetencyQuestionDto fromEntity(CoreCompetencyQuestion coreCompetencyQuestion) {
        return UserCoreCompetencyQuestionDto.builder()
                .questionId(coreCompetencyQuestion.getId())
                .questionNo(coreCompetencyQuestion.getQuestionNo())
                .questionName(coreCompetencyQuestion.getName())
                .responseChoiceOptions(
                        coreCompetencyQuestion.getResponseChoiceOptions().stream()
                                .map(ResponseChoiceOptionDto::fromEntity) // 보기 항목도 DTO로 변환
                                .toList()
                )
                .build();
    }
}
