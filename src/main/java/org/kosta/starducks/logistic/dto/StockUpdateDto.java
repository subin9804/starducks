package org.kosta.starducks.logistic.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDto {

    private Long productCode;
    private Long storeNo;

    private int inventoryCnt;


}
