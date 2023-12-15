package org.kosta.starducks.fina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Long productCode;   // 물품번호
    private String productName; // 물품명
    private int productCnt; // 물품 수량(총무부 - 품목을 등록할 때 수량은 0)
    private Long productPrice;  // 가격
    private ProductCategory productCategory;
    private ProductUnit productUnit;
    private boolean productSelling;

}
