package org.kosta.starducks.logistic.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.kosta.starducks.hr.entity.EmpEntity;

import java.time.LocalDateTime;
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
    private EmpEntity empEntity;


    @OneToMany(mappedBy = "warehouseInbound",cascade = CascadeType.ALL)
    private List<WarehouseInboundProduct> inboundProducts = new ArrayList<>();
    //연관관계의 주인인 Orderproduct 엔티티에서 order라는 이름으로 저장된 변수가 이 엔티티를 관리한다.
    //배열에는 orderProduct의 엔터티 객체를 담고 있다.
    //cascade all으로 설정하여 이 관계에서 발생하는 모든 변경이 해당 관계에 속하는 모든 엔터티에 전파되도록 한다.




    private LocalDateTime warehouseInboundDate;


    private long totalPrice;
    private int totalQuantity;



}
