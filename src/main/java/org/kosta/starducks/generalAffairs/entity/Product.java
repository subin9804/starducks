package org.kosta.starducks.generalAffairs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.kosta.starducks.logistic.entity.StoreInventory;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_code")
    private Long productCode;

    @Column(nullable = false)
    private String productName;

    /*
    품목을 등록할 때 수량은 0이다.
     */
    @Column(columnDefinition = "int default 0")
    private int productCnt;
    /*
    품목의 단위당 가격을 표시한다.
     */
    @Column(nullable = false)
    private Long productPrice;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "product_category",nullable = false)
    private ProductCategory productCategory;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "product_unit",nullable = false)
    private ProductUnit productUnit;

    @Column(nullable = false)
    private boolean productSelling;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id",nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Vendor vendor;

    @Column(length = 150)
    private String fileName;

    @Column(length = 300)
    private String filePath;


    @OneToMany(mappedBy ="product")
    private List<StoreInventory> inventories;


    public void increaseCnt(int inboundQuantity){
        int totalQuantity = this.productCnt + inboundQuantity;

        this.productCnt = totalQuantity;
    }
    public void decreaseCnt(int outboundQuantity){
        int totalQuantity = this.productCnt - outboundQuantity;

        this.productCnt = totalQuantity;
    }

}
