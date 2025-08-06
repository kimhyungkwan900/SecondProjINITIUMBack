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

    private Long questionId;
    private Integer questionNo;      // 문항 번호
    private String questionName;     // 문항 내용

    //함께 등록될 보기 리스트
    List<ResponseChoiceOptionDto> responseChoiceOptions;

    public static UserCoreCompetencyQuestionDto fromEntity(CoreCompetencyQuestion coreCompetencyQuestion) {
        return UserCoreCompetencyQuestionDto.builder()
                .questionId(coreCompetencyQuestion.getId())
                .questionNo(coreCompetencyQuestion.getQuestionNo())
                .questionName(coreCompetencyQuestion.getName())
                .responseChoiceOptions(coreCompetencyQuestion.getResponseChoiceOptions().stream()
                        .map(ResponseChoiceOptionDto::fromEntity).toList())
                .build();
    }
}
