package org.kosta.starducks.logistic.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WarehouseOutboundDto {

    @JsonProperty("empId")
    private Long empId;
    @JsonProperty("productCode")
    private Long productCode;
    @JsonProperty("outboundQuantity")
    private int outboundQuantity;

}
