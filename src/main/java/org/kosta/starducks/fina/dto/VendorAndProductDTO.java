package org.kosta.starducks.fina.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.VendorBusinessSector;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendorAndProductDTO {
    private int vendorId;   // 거래처 번호
    private String vendorName;  // 거래처명

    @Enumerated(EnumType.STRING)
    private VendorBusinessSector businessSector;    // 거래처 업종
    private String vendorRepreName; // 대표자명
    private String vendorRegistNum; // 사업자 등록번호
    private String vendorTelephone; // 거래처 연락처('-' 포함)
    private LocalDate vendorStartDate;  // 거래 시작일

    private String vendorAddNo; // 우편번호
    private String vendorAddress; // 거래처 주소
    private String vendorDetailAdd; // 상세 주소

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus; // 계약 상태

//    private List<Product> products;
}

/**
 * 재무부에서 계약 종료를 하면 총무부의 품목관리에서는 계약 종료의 경우 해당 거래처가 화면에 뜨지 않는다.
 * 그러나 계약 중일 경우 총무부의 품목관리 등록에서 해당 거래처가 드롭다운으로 뜬다
 * 계약 중과 계약 완료는 체크박스로 한다.
 */