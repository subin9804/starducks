package org.kosta.starducks.logistic.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.StoreInventory;
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
}
