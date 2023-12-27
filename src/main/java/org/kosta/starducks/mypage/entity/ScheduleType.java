package org.kosta.starducks.mypage.entity;

import lombok.Getter;

@Getter
public enum ScheduleType {
    PERSONAL_SCHEDULE("개인 일정"),
    OFFICIAL_SCHEDULE("공식 일정");

    private final String displayName;

    ScheduleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
