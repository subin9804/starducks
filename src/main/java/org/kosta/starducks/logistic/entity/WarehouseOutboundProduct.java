package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.generalAffairs.entity.Product;

@Entity
@Data
public class WarehouseOutboundProduct {

    //입고된 상품 (한 종류의 상품 몇개 샀는가)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseOutboundProductId;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "warehouse_outbound_id", nullable = false)
    private WarehouseOutbound warehouseOutbound;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private long outboundPrice;

    private int outboundQuantity;
    //몇개 입고 시켰는가?


    public static WarehouseOutboundProduct createWarehouseProduct(Product product, int outboundQuantity) {

        // 입고 상품 생성
        WarehouseOutboundProduct woProduct = new WarehouseOutboundProduct();


        woProduct.setProduct(product);
        woProduct.setOutboundPrice(product.getProductPrice()*outboundQuantity);
        woProduct.setOutboundQuantity(outboundQuantity);

        //재고 증가 로직
        
        return woProduct;

    }






}
