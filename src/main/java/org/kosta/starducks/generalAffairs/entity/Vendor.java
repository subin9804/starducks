package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Vendor {
    /**
     * 공급업체
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;   // 거래처번호

    private String vendorName;  // 거래처명
    private String vendorRegistNum; // 사업자등록번호
    private String vendorRepreName; // 대표자명
    private String vendorTelephone; // 거래처 연락처('-' 포함)
    private LocalDate vendorStartDate;  // 거래시작일
    private String vendorAddress;   // 거래처 주소

//    @Enumerated(EnumType.STRING)
//    private ContractStatus contractStatus; // 계약 상태

    /** Product 추가*/
    /**
     * Vendor는 여러 Product를 가질 수 있다
     */
//    @OneToMany(mappedBy = "vendor")
//    private List<Product> products;
}
