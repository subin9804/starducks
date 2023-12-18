package org.kosta.starducks.logistic.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
public class StoreInboundDto {

    @JsonProperty("productCode")
    private Long productCode;

    @JsonProperty("storeNo")
    private Long storeNo;

    @JsonProperty("inboundQuantity")
    private int inboundQuantity;

}
