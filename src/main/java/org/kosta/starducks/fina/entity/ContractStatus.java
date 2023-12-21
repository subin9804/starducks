package org.kosta.starducks.fina.entity;

import lombok.Getter;

@Getter
public enum ContractStatus {
    CONTRACT_ACTIVE("계약 중"),

    CONTRACT_STOPPED("계약 중지");

    private final String displayName;

    ContractStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
