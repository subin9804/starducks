package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

/**
 * 공급업체 (-> 재무부 거래처관리와 연결)
 */
@Entity
@Data
public class Vendor {

    @Id
    @GeneratedValue
    private int vendorId;   // 거래처번호
    private String vendorName;  // 거래처명
    private String vendorRegistNum; // 거래처 등록 번호
    private String vendorRepreName; // 대표자명
    private String vendorTelephone; // 거래처 연락처
    private LocalDate vendorStartDate;  // 거래 시작일
    private String vendorAddress;   // 거래처 주소

    // 거래처 업종, 거래 여부 추가(재무부)
    private String venderSector;    // 거래처 업종

}
