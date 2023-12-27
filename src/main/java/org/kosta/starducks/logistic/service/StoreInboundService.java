package org.kosta.starducks.logistic.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.logistic.dto.StoreInboundDto;
import org.kosta.starducks.logistic.dto.WarehouseInboundDto;
import org.kosta.starducks.logistic.entity.*;
import org.kosta.starducks.logistic.repository.StoreInboundRepository;
import org.kosta.starducks.logistic.repository.StoreInventoryRepository;
import org.kosta.starducks.logistic.repository.WarehouseInboundRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreInboundService {


    private final StoreInboundRepository stRepository;
    private final EmpRepository empRepository;
    private final ProductRepository productRepository;
    private final StoreInventoryRepository storeInventoryRepository;
    private final StoreRepository storeRepository;

    //모든 입고 사항 가져오기
    public Page<StoreInbound> getAllInbounds(Pageable pageable){
        return stRepository.findAll(pageable);
    }

//    public List<StoreInbound> findRecentHighTotalPriceInbounds() {
//        // 위에서 작성한 쿼리 메서드 활용
//        return stRepository.findRecentHighTotalPriceInbounds();
//    }
    public List<StoreInbound> getAllInboundsByStoreNo(Long storeNo){
        return stRepository.findStoreInboundsByStore_StoreNo(storeNo);
    }
    public StoreInbound getInboundByInboundId(Long inboundId){

        return stRepository.findById(inboundId).get();
    }

    public void addStoreInbound(List<StoreInboundDto> storeInboundDtos) {//userid,[productId,orderQuantity]


        StoreInbound storeInbound1 = new StoreInbound();

        //Employee employee = empRepository.findById(storeInboundDtos.get(0).getEmpId()).get();

        Store store = storeRepository.findById(storeInboundDtos.get(0).getStoreNo()).get();


        for (StoreInboundDto stDto : storeInboundDtos) {
            if ( stDto.getProductCode() != null) {
            Product product = productRepository.findById(stDto.getProductCode()).orElse(null);

//                if (product != null) {
//                    // 로깅 추가
//                    System.out.println("Product Code: " + stDto.getProductCode());
//                    continue; // 다음 iteration으로 건너뛰기
//                }
//                // store 검색
//                if (store == null) {
//                    // 로깅 추가
//                    System.out.println("Store not found for store number: " + stDto.getStoreNo());
//                    continue; // 다음 iteration으로 건너뛰기
//                }

            if (store != null && product != null) {
                //입고 상품 생성
                StoreInboundProduct storeProduct = StoreInboundProduct.createStoreProduct(product, stDto.getInboundQuantity());
                //주문생성 -> 여러개의 입고물품에 대해서 1개의 주문 내역만 생성되도록 만들고 싶음.
                //for문 안에 들어가면 안됨.
                //WarehouseInbound 객체 안에 있는 inboundProducts 리스트 객체 안으로 들어가야함.
                storeInbound1.addOrderProduct(storeProduct);
                // 여기서 재고 업데이트
                updateStock(store,product,stDto.getInboundQuantity());
            }
            }
            }

        List<StoreInboundProduct> inboundProducts = storeInbound1.getInboundProducts();

        StoreInbound storeInbound = StoreInbound.createInbound(store,inboundProducts);
        stRepository.save(storeInbound);



    }

    private void updateStock(Store store, Product product, int inboundQuantity) {

        StoreInventory storeInventory = storeInventoryRepository.findByStore_StoreNoAndProduct_ProductCode(store.getStoreNo(),product.getProductCode());

        if(storeInventory != null){
            //기존의 해당 항목이 있으면 수량만 증가시켠 된다.
            storeInventory.increaseCnt(inboundQuantity);

        } else {
            //기존에 해당 항목이 없으면, 목록에 새롭게 추가해야한다.
            storeInventory = new StoreInventory();
            StoreInventoryId storeInventoryId = new StoreInventoryId();
            storeInventory.setProduct(product);
            storeInventory.setStore(store);


            //복합 키가 적용되었으므로 자동으로 Id가 생성되는 게 아니므로,id값 역시 setter로 설정해주어야 한다.
            storeInventory.setId(storeInventoryId);

            //입고량 등록
            storeInventory.setInventoryCnt(inboundQuantity);
        }
        //StoreInventory 엔티티에 저장한다.
        storeInventoryRepository.save(storeInventory);


    }




//    public void addWarehouseInbound(List<WarehouseInboundDto> warehouseInboundDtos) {//userid,[productId,orderQuantity]
//
//
//
//        for (WarehouseInboundDto wiDto : warehouseInboundDtos) {
//            if (wiDto.getEmpId() != null && wiDto.getProductCode() != null) {
//                Employee employee = empRepository.findById(wiDto.getEmpId()).orElse(null);
//                Product product = productRepository.findById(wiDto.getProductCode()).orElse(null);
//
//                if (employee != null && product != null) {
//                    //입고 상품 생성
//                    WarehouseInboundProduct warehouseProduct = WarehouseInboundProduct.createWarehouseProduct(product, wiDto.getInboundQuantity());
//                    //주문생성 -> 여러개의 입고물품에 대해서 1개의 주문 내역만 생성되도록 만들고 싶음.
//                    //for문 안에 들어가면 안됨.
//                    //WarehouseInbound 객체 안에 있는 inboundProducts 리스트 객체 안으로 들어가야함.
//
//
//                    WarehouseInbound warehouseInbound = WarehouseInbound.createInbound(employee, warehouseProduct);
//                    wiRepository.save(warehouseInbound);
//
//                    // 여기서 재고 업데이트
//                    updateStock(product, wiDto.getInboundQuantity());
//                }
//            }
//        }
//
//
//    }
//
//    private void updateStock(Product product, int inboundQuantity) {
//        product.setProductCnt(product.getProductCnt() + inboundQuantity);
//        productRepository.save(product);
//    }
//    public void cancelOrder(long orderId) {
//        Order order = orderRepository.findById(orderId).get();
//        order.cancel();
//
//        productRepository.save(order.getOrderProducts().get(0).getProduct());
//        orderRepository.save(order);
//    }

//    public Order getOrderInfo(Long orderId){
//        return orderRepository.findById(orderId).get();
//    }

//    public void updateOrder(Order order){
//        orderRepository.save(order);
//    }





}
