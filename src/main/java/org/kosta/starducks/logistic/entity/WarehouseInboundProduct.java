package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.kosta.starducks.generalAffairs.entity.Product;

@Entity
@Data
public class WarehouseInboundProduct {

    //입고된 상품 (한 종류의 상품 몇개 샀는가)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseInboundProductId;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "warehouse_inbound_id", nullable = false)
    private WarehouseInbound warehouseInbound;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



    @Column(name = "order_quantity")
    private int warehouseInboundProductQuantity;
    //몇개 샀는가


}
