package org.kosta.starducks.document.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class OrderItem {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long orderItemId;

    private Long productCode;

    private int quantity;


    // 생성자, getter, setter 등 필요한 메서드 추가


    // 생성자
    public OrderItem(long productCode, int quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Document document;
}

