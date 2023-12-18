package org.kosta.starducks.logistic.repository;

import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.StoreInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StoreInboundRepository extends JpaRepository<StoreInbound,Long>{
    List<StoreInbound> findStoreInboundsByStore_StoreNo(Long storeNo);


    //List<StoreInbound> findStoreInboundsByEmployee_EmpId(Long empId);


}
