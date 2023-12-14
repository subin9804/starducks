package org.kosta.starducks.fina.entity;

import lombok.Getter;

@Getter
public enum StoreOperationalYn {
    storeOperationalY("운영"), storeOperationalN("미운영");

    private final String description;

    StoreOperationalYn(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
