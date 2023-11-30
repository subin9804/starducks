package org.kosta.starducks.generalAffairs.dto;


import lombok.Getter;
import lombok.Setter;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;

@Getter
@Setter
public class ProductUpdateDto {

    private Long productCode;

    private ProductCategory productCategory;

    private ProductUnit productUnit;

    private Long productPrice;

    private boolean productSelling;



}
