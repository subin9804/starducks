package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.generalAffairs.entity.Product;

import java.util.Date;

@Entity
@Data
public class BranchInventory {
    //지점 재고 entity
    @Id
    private Long inventoryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;



    private int inventoryQuantity;
    private Date LastUpdateDate;

}
