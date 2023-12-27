package org.kosta.starducks.logistic.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.logistic.dto.StockUpdateDto;
import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.StoreInventory;
import org.kosta.starducks.logistic.entity.StoreInventoryId;
import org.kosta.starducks.logistic.repository.StoreInventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreInventoryService {

    private final StoreInventoryRepository storeInventoryRep;

    //재고 리스트 처리
    @Transactional
    public Page<StoreInventory> getAllStoreInventories(Pageable pageable){
        return storeInventoryRep.findAll(pageable);

    }

    public Page<StoreInventory> storeInventorySearchList(String searchKeyword, Pageable pageable){
        return storeInventoryRep.findByProduct_ProductNameContaining(searchKeyword,pageable);
    }




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
