package com.secondprojinitiumback.admin.coreCompetency.domain;

public enum LevelTypeEnum {
    CORE_COMPETENCY, SUB_COMPETENCY;

    public boolean equalsIgnoreCase(String levelType) {
        if (levelType == null) {
            return false;
        }
        switch (this) {
            case CORE_COMPETENCY:
                return levelType.equalsIgnoreCase("핵심역량");
            case SUB_COMPETENCY:
                return levelType.equalsIgnoreCase("하위역량");
            default:
                return false;
        }
    }
}