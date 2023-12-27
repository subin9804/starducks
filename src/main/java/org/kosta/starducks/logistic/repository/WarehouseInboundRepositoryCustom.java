package org.kosta.starducks.logistic.repository;

import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WarehouseInboundRepositoryCustom {
    //QueryDSL로 수행할 메서드들을 선언해주어야 함.

    List<WarehouseInbound> findRecentHighTotalPriceInbounds();

    Page<WarehouseInbound> findRecentHighTotalAmountAndPrice(Pageable pageable);
}
