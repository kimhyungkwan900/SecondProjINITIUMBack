package com.secondprojinitiumback.user.diagnostic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosisRequestDto {

    @NotBlank(message = "학생 번호는 필수입니다.")
    private String studentNo;

    @NotBlank(message = "qestrnSeq는 필수입니다.") // V1 검사번호
    private String qestrnSeq;

    @NotBlank(message = "대상 코드(trgetSe)는 필수입니다.")
    private String trgetSe;

    @NotBlank(message = "문항별 응답은 필수입니다.")
    private String answers; // "1=5 2=7 3=3 ..." 형식

    @NotBlank(message = "성별은 필수입니다.")
    private String gender;

    private String school; // 선택

    @NotBlank(message = "학년은 필수입니다.")
    private String grade;

    @NotBlank(message = "시작일시는 필수입니다.")
    private String startDtm;
}
