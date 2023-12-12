package org.kosta.starducks.fina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fina_store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no")
    private Long storeNo;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_businessNum")
    private Long businessNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "storeManage")
    private Employee employee;

    @Column(name = "store_open_date")
    private LocalDate storeOpenDate;

    private String addNo;
    private String storeAddr;
    private String storeDetailAddr;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_operational_yn")
    private storeOperationalYn storeOperationalYn;

}
