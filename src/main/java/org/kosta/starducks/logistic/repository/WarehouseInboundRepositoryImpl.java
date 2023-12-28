package org.kosta.starducks.logistic.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.logistic.entity.QWarehouseInbound;
import org.kosta.starducks.logistic.entity.QWarehouseOutbound;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;


public class WarehouseInboundRepositoryImpl extends QuerydslRepositorySupport implements WarehouseInboundRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public WarehouseInboundRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(WarehouseInbound.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

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


    @Override
    public Page<WarehouseInbound> findRecentHighTotalAmountAndPrice(Pageable pageable) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        // Create the base query
        JPAQuery<WarehouseInbound> baseQuery = jpaQueryFactory
                .selectFrom(QWarehouseInbound.warehouseInbound)
                .where(
                        QWarehouseInbound.warehouseInbound.warehouseInboundDate.after(oneMonthAgo),
                        QWarehouseInbound.warehouseInbound.totalQuantity.gt(100)


                );

        // Fetch the total count before applying pagination
        long total = baseQuery.fetchCount();

        // Apply pagination information from the Pageable
        JPAQuery<WarehouseInbound> paginatedQuery = (JPAQuery<WarehouseInbound>) getQuerydsl().applyPagination(pageable, baseQuery);

        // Fetch the paginated results
        List<WarehouseInbound> results = paginatedQuery.fetch();

        return new PageImpl<>(results, pageable, total);
    }


}
