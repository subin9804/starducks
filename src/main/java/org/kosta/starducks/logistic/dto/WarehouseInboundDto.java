package org.kosta.starducks.logistic.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.kosta.starducks.logistic.entity.WarehouseInboundProduct;
import org.kosta.starducks.logistic.repository.StoreInboundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@NoArgsConstructor
public class WarehouseInboundDto {

    @JsonProperty("empId")
    private Long empId;
    @JsonProperty("productCode")
    private Long productCode;
    @JsonProperty("inboundQuantity")
    private int inboundQuantity;

}
