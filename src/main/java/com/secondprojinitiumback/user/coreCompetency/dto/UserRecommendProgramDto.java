package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecommendProgramDto {

    private Long programId;
    private String eduNm;   //프로그램 이름
    private Long subCategoryId; //하위역량카테고리Id
    private String studentNo;
    private String assessmentNo;
    private Integer eduMlg;    //마일리지
    private LocalDateTime eduAplyBgngDt; // 신청 시작일
    private LocalDateTime eduAplyEndDt; // 신청 마감일
    private LocalDate eduBgngYmd; // 교육 시작일
    private LocalDate eduEndYmd; // 교육 종료일
}
