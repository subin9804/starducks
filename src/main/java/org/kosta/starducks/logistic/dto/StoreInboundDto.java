package org.kosta.starducks.logistic.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreInboundDto {

    @JsonProperty("empId")
    private Long empId;
    @JsonProperty("productCode")
    private Long productCode;

    @JsonProperty("storeNo")
    private Long storeNo;

    @JsonProperty("inboundQuantity")
    private int inboundQuantity;
}
