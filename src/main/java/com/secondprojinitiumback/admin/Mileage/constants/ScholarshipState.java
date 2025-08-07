package com.secondprojinitiumback.admin.Mileage.constants;

import com.secondprojinitiumback.common.domain.CommonCodeId;

public enum ScholarshipState {

    APPLY("1", "신청"),
    APPROVE("2", "승인"),
    REJECT("3", "반려"),
    PAYMENT("4", "지급");

    public static final String CODE_GROUP = "MG0001";

    private final String code;
    private final String label;

    ScholarshipState(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    // CommonCodeId로 변환 (DB 조회용)
    public CommonCodeId toCommonCodeId() {
        return new CommonCodeId(CODE_GROUP, code);
    }

    // 코드 비교용 유틸
    public boolean matches(CommonCodeId id) {
        return id != null &&
                CODE_GROUP.equals(id.getCodeGroup()) &&
                code.equals(id.getCode());
    }
}

