package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.generalAffairs.entity.Product;

@Entity
@Data
public class StoreInboundProduct {

    //입고된 상품 (한 종류의 상품 몇개 샀는가)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeInboundProductId;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "store_inbound_id", nullable = false)
    private StoreInbound storeInbound;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private long inboundPrice;

    private int inboundQuantity;
    //몇개 입고 시켰는가?


    public static StoreInboundProduct createStoreProduct(Product product, int inboundQuantity) {

        // 입고 상품 생성
        StoreInboundProduct wiProduct = new StoreInboundProduct();


        wiProduct.setProduct(product);
        wiProduct.setInboundPrice(product.getProductPrice()*inboundQuantity);
        wiProduct.setInboundQuantity(inboundQuantity);

        //재고 증가 로직
        product.increaseCnt(inboundQuantity);
        return wiProduct;

    }






}
