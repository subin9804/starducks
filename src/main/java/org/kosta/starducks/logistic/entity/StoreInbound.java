package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class StoreInbound {

    //물류 창고 재고증가
    //물류 창고의 재고는 물류 유통부 직원이 입고를 등록해야 증가한다.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StoreInboundId;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "emp_id",nullable = false)
//    private Employee employee;

    //지점의 아이디 있어야 함 -> 누가 주문했는지 알아야 하니까
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_no",nullable = false)
    private Store store;


    @OneToMany(mappedBy = "storeInbound",cascade = CascadeType.ALL)
    private List<StoreInboundProduct> inboundProducts = new ArrayList<>();
    //연관관계의 주인인 StoreInboundProduct 엔티티에서 StoreInbound라는 이름으로 저장된 변수가 이 엔티티를 관리한다.
    //배열에는 storeInboundProduct의 엔터티 객체를 담고 있다.
    //cascade all으로 설정하여 이 관계에서 발생하는 모든 변경이 해당 관계에 속하는 모든 엔터티에 전파되도록 한다.



    //@CreationTimestamp
    private LocalDateTime storeInboundDate;


    private long totalPrice;
    private int totalQuantity;



    public void addOrderProduct(StoreInboundProduct stproduct) {
        inboundProducts.add(stproduct);
        //리스트에 받아온 product을 넣는다.
        stproduct.setStoreInbound(this);
        //입고에 넣어줘라!!!

    }


    public static StoreInbound createInbound(Store store, List<StoreInboundProduct> stproducts) {

        //order 생성
        Long totalPrice = 0L;
        int totalQuantity = 0;

        StoreInbound storeInbound = new StoreInbound();
        storeInbound.setStore(store);

        LocalDateTime now = LocalDateTime.now();

        //포맷팅을 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        formattedDateTime = formattedDateTime.replace("T", " ");
        storeInbound.setStoreInboundDate(LocalDateTime.parse(formattedDateTime, formatter));



        for(StoreInboundProduct stProduct :stproducts) {
            totalPrice += stProduct.getInboundPrice();
            totalQuantity += stProduct.getInboundQuantity();
            storeInbound.addOrderProduct(stProduct);
        }

        storeInbound.setTotalPrice(totalPrice);
        storeInbound.setTotalQuantity(totalQuantity);


        return storeInbound;

    }


}
