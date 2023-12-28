package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.VendorBusinessSector;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vendor {
    /**
     * 공급업체
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;   // 거래처번호

    @Enumerated(EnumType.STRING)
    private VendorBusinessSector vendorBusinessSector;  // 업종

    private String vendorName;  // 거래처명
    private String vendorRegistNum; // 사업자등록번호
    private String vendorRepreName; // 대표자명
    private String vendorTelephone; // 거래처 연락처('-' 포함)
    private LocalDate vendorStartDate;  // 거래시작일
//    private String vendorAddress;   // 거래처 주소

    private String vendorAddNo; // 우편번호
    private String vendorAddress; // 거래처 주소
    private String vendorDetailAdd; // 상세 주소

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus; // 계약 상태

    /** Product 추가*/
    /**
     * Vendor는 여러 Product를 가질 수 있다
     */
    @OneToMany(mappedBy = "vendor")
    @ToString.Exclude
    private List<Product> products;
}
