package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class WarehouseInbound {

    //물류 창고 재고증가
    //물류 창고의 재고는 물류 유통부 직원이 입고를 등록해야 증가한다.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseInboundId;

    //사원의 아이디 있어야 함 -> 누가 주문했는지 알아야 하니가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id",nullable = false)
    private Employee employee;


    @OneToMany(mappedBy = "warehouseInbound",cascade = CascadeType.ALL)
    private List<WarehouseInboundProduct> inboundProducts = new ArrayList<>();
    //연관관계의 주인인 Orderproduct 엔티티에서 order라는 이름으로 저장된 변수가 이 엔티티를 관리한다.
    //배열에는 orderProduct의 엔터티 객체를 담고 있다.
    //cascade all으로 설정하여 이 관계에서 발생하는 모든 변경이 해당 관계에 속하는 모든 엔터티에 전파되도록 한다.

    //@CreationTimestamp
    private LocalDateTime warehouseInboundDate;


    private long totalPrice;
    private int totalQuantity;



    public void addOrderProduct(WarehouseInboundProduct wiproduct) {
        inboundProducts.add(wiproduct);
        //리스트에 받아온 product을 넣는다.
        wiproduct.setWarehouseInbound(this);
        //입고에 넣어줘라!!!

    }


    public static WarehouseInbound createInbound(Employee employee, List<WarehouseInboundProduct> wiproducts) {

        //order 생성
        Long totalPrice = 0L;
        int totalQuantity = 0;

        WarehouseInbound warehouseInbound = new WarehouseInbound();
        warehouseInbound.setEmployee(employee);

        LocalDateTime now = LocalDateTime.now();

        //포맷팅을 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        formattedDateTime = formattedDateTime.replace("T", " ");
        warehouseInbound.setWarehouseInboundDate(LocalDateTime.parse(formattedDateTime, formatter));



        for(WarehouseInboundProduct wiProduct :wiproducts) {
            totalPrice += wiProduct.getInboundPrice();
            totalQuantity += wiProduct.getInboundQuantity();
            warehouseInbound.addOrderProduct(wiProduct);
        }

        warehouseInbound.setTotalPrice(totalPrice);
        warehouseInbound.setTotalQuantity(totalQuantity);


        return warehouseInbound;

    }


}
