package org.kosta.starducks.generalAffairs.dto;


import lombok.Getter;
import lombok.Setter;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;

@Getter
@Setter
public class ProductStockUpdateDto {

    private Long productCode;

    private int productCnt;



}
