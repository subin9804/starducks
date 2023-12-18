package org.kosta.starducks.logistic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class StoreInventoryId implements Serializable {

    @Column(name = "product_code", nullable = false)
    private Long productCode;

    @Column(name = "store_no", nullable = false)
    private Long storeNo;

    //생성자 및 getter setter 추가


    @Override
    public boolean equals(Object obj) {

        if(this == obj) return true;
        if (obj ==null || getClass() != obj.getClass()) return false;

        StoreInventoryId that = (StoreInventoryId) obj;
        return Objects.equals(productCode, that.productCode) &&
                Objects.equals(storeNo, that.storeNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productCode,storeNo);

    }
}
