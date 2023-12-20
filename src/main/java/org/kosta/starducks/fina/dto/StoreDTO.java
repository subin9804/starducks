package org.kosta.starducks.fina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.entity.StoreOperationalYn;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StoreDTO {

    private Long storeNo;  // 지점 번호
    private String storeName;  // 지점명
    private Long businessNum;   // 사업자번호
    private Employee employee;    // 점장
    private LocalDate storeOpenDate;   // 개업일
    private String addNo;   // 우편번호
    private String storeAddr;  // 주소
    private String storeDetailAddr;    // 상세주소
    private StoreOperationalYn storeOperationalYn; // 운영 여부


//    public Store toEntity() {
//        return new Store(storeNo, storeName, businessNum, employee, storeOpenDate,
//                addNo, storeAddr, storeDetailAddr,storeOperationalYn);
//    }
}
