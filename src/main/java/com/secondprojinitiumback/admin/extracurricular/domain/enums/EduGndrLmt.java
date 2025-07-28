package com.secondprojinitiumback.admin.extracurricular.domain.enums;

public enum EduGndrLmt {
    MALE("10"),
    FEMALE("20");

    private String code;
    EduGndrLmt(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
    public static EduGndrLmt fromCode(String code) {
        for (EduGndrLmt value : EduGndrLmt.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
