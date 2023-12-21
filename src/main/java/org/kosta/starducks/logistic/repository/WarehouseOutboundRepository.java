package org.kosta.starducks.logistic.repository;

import org.kosta.starducks.logistic.entity.WarehouseOutbound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WarehouseOutboundRepository extends JpaRepository<WarehouseOutbound,Long>,WarehouseOutboundRepositoryCustom {


    List<WarehouseOutbound> findWarehouseOutboundsByEmployee_EmpId(Long empId);




}
