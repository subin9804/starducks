package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.generalAffairs.entity.Product;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Data
public class StoreInventory extends BaseTimeEntity implements Serializable {

    //지점 재고 entity

    @EmbeddedId
    //복합식별자 지정하기
    private StoreInventoryId id;

    @MapsId("productCode") //복합키를 가진 엔티티에서 식별자 매핑할 때 사용된다.
    @ManyToOne
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;


    @MapsId("storeNo")
    @ManyToOne
    @JoinColumn(name = "store_no", nullable = false)
    private Store store;


    //지점재고입니다
    @Column
    private int inventoryCnt;



    public void increaseCnt(int inboundQuantity){
        int totalQuantity = this.inventoryCnt + inboundQuantity;

        this.inventoryCnt = totalQuantity;

        // store와 product가 null인 경우 초기화
        if (this.store == null) {
            this.store = new Store();  // Store 클래스에 대한 생성자가 필요함
        }

        if (this.product == null) {
            this.product = new Product();  // Product 클래스에 대한 생성자가 필요함
        }
    }


}
