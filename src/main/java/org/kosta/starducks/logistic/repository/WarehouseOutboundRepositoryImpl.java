package org.kosta.starducks.logistic.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.logistic.entity.QWarehouseInbound;
import org.kosta.starducks.logistic.entity.QWarehouseOutbound;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.kosta.starducks.logistic.entity.WarehouseOutbound;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class WarehouseOutboundRepositoryImpl implements WarehouseOutboundRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

  @Override
    public List<WarehouseOutbound> findRecentHighTotalPriceOutbounds() {

        /*
        SELECT *
        FROM warehouse_inbound
        WHERE
        */

      //한달 이내에 백만원이상이거나 100개이상의 출고가 이루어 진 경우
      LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
      BooleanBuilder whereBuilder = new BooleanBuilder();
      whereBuilder.and(QWarehouseOutbound.warehouseOutbound.warehouseOutboundDate.after(oneMonthAgo));
      whereBuilder.or(QWarehouseOutbound.warehouseOutbound.totalPrice.gt(1000000L));
      whereBuilder.or(QWarehouseOutbound.warehouseOutbound.totalQuantity.gt(100));

      return jpaQueryFactory
              .selectFrom(QWarehouseOutbound.warehouseOutbound)
              .where(whereBuilder)
              .fetch();


    }

}
