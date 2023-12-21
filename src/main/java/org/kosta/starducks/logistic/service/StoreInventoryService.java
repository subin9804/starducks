package org.kosta.starducks.logistic.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.logistic.dto.StockUpdateDto;
import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.StoreInventory;
import org.kosta.starducks.logistic.entity.StoreInventoryId;
import org.kosta.starducks.logistic.repository.StoreInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreInventoryService {

    private final StoreInventoryRepository storeInventoryRep;
    public List<StoreInventory> getAllInventories(){
        return storeInventoryRep.findAll();
    }



    public StoreInventory getInventoryByNoAndCode(Long no, Long code) {

        return storeInventoryRep.findByStore_StoreNoAndProduct_ProductCode(no,code);
    }

    public void updateStock(StockUpdateDto stockUpdateDto) {

        StoreInventory foundInventory = storeInventoryRep.findByStore_StoreNoAndProduct_ProductCode(stockUpdateDto.getStoreNo(), stockUpdateDto.getProductCode());
        foundInventory.setInventoryCnt(stockUpdateDto.getInventoryCnt());
        storeInventoryRep.save(foundInventory);

    }
}
