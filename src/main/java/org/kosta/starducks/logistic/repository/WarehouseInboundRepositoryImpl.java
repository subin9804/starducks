package org.kosta.starducks.logistic.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.logistic.entity.QWarehouseInbound;
import org.kosta.starducks.logistic.entity.WarehouseInbound;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class WarehouseInboundRepositoryImpl implements WarehouseInboundRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

  @Override
    public List<WarehouseInbound> findRecentHighTotalPriceInbounds() {

        /*
        SELECT *
        FROM warehouse_inbound
        WHERE
        */

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        return jpaQueryFactory
                .selectFrom(QWarehouseInbound.warehouseInbound)
                .where(
                        QWarehouseInbound.warehouseInbound.warehouseInboundDate.after(oneMonthAgo),
                        QWarehouseInbound.warehouseInbound.totalPrice.gt(1000000L)
                )
                .fetch();
    }

}
