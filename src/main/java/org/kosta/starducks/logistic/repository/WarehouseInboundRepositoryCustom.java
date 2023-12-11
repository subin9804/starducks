package org.kosta.starducks.logistic.repository;

import org.kosta.starducks.logistic.entity.WarehouseInbound;

import java.util.List;

public interface WarehouseInboundRepositoryCustom {
    //QueryDSL로 수행할 메서드들을 선언해주어야 함.

    List<WarehouseInbound> findRecentHighTotalPriceInbounds();

}
