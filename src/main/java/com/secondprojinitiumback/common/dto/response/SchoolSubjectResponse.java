package com.secondprojinitiumback.common.dto.response;

import com.secondprojinitiumback.common.domain.SchoolSubject;
import lombok.Value;


@Value
public class SchoolSubjectResponse {
    String subjectCode;
    String subjectName;

    public static SchoolSubjectResponse from(SchoolSubject s) {
        return new SchoolSubjectResponse(
                s.getSubjectCode(),
                s.getSubjectName()
        );
    }

    @Value
    public static class Option {
        String code;
        String label;

        public static Option from(SchoolSubject s) {
            return new Option(s.getSubjectCode(), s.getSubjectName());
        }
    }
}