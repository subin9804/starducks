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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "product_category")
    private ProductCategory productCategory;

    private String productName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "product_unit")
    private ProductUnit productUnit;

    private Long productPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;




}
