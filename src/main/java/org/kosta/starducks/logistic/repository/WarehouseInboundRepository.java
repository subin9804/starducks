package org.kosta.starducks.logistic.repository;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface WarehouseInboundRepository extends JpaRepository<WarehouseInbound,Long> {


    List<WarehouseInbound> findWarehouseInboundsByEmployee_EmpId(Long empId);

}
