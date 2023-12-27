package org.kosta.starducks.logistic.repository;

import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.StoreInventory;
import org.kosta.starducks.logistic.entity.StoreInventoryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StoreInventoryRepository extends JpaRepository<StoreInventory, StoreInventoryId>{


    List<StoreInventory> findByStore_StoreNo(Long storeNo);
    List<StoreInventory> findByProduct_ProductCode(Long productCode);



    StoreInventory findByStore_StoreNoAndProduct_ProductCode(Long storeNo, Long productCode);



    Page<StoreInventory> findByProduct_ProductNameContaining(String searchKeyword, Pageable pageable);

}
