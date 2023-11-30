package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id",nullable = false)
    private Vendor vendor;

}
