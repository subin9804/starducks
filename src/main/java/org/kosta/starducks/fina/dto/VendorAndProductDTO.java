package org.kosta.starducks.fina.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.fina.entity.VendorBusinessSector;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    private List<ProductDTO> productDTO;

    public VendorAndProductDTO toEntity() {
        return new VendorAndProductDTO(vendorId, vendorName, businessSector,
                vendorRepreName, vendorRegistNum, vendorTelephone, vendorStartDate,
                vendorAddNo, vendorAddress, vendorDetailAdd, productDTO);
    }
}
