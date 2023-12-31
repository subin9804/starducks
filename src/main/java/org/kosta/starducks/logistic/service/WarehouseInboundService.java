package org.kosta.starducks.logistic.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.logistic.dto.WarehouseInboundDto;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.kosta.starducks.logistic.entity.WarehouseInboundProduct;
import org.kosta.starducks.logistic.repository.WarehouseInboundRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseInboundService {


    private final WarehouseInboundRepository wiRepository;
    private final EmpRepository empRepository;
    private final ProductRepository productRepository;

    //모든 입고 사항 가져오기

    public Page<WarehouseInbound> getAllWarehouseInbounds(Pageable pageable){
       return wiRepository.findAll(pageable);

    }
    public Page<WarehouseInbound> findRecentHighTotalAmountAndPrice(Pageable pageable){
        return wiRepository.findRecentHighTotalAmountAndPrice(pageable);
    }
    public List<WarehouseInbound> getAllInbounds(){
        return wiRepository.findAll();
    }
    public List<WarehouseInbound> findRecentHighTotalPriceInbounds() {
        // 위에서 작성한 쿼리 메서드 활용
        return wiRepository.findRecentHighTotalPriceInbounds();
    }
    public List<WarehouseInbound> getAllInboundsByEmpId(Long empId){
        return wiRepository.findWarehouseInboundsByEmployee_EmpId(empId);
    }
    public WarehouseInbound getInboundByInboundId(Long inboundId){

        return wiRepository.findById(inboundId).get();
    }

    public void addWarehouseInbound(List<WarehouseInboundDto> warehouseInboundDtos) {//userid,[productId,orderQuantity]


        WarehouseInbound warehouseInbound1 = new WarehouseInbound();

        Employee employee = empRepository.findById(warehouseInboundDtos.get(0).getEmpId()).get();


        for (WarehouseInboundDto wiDto : warehouseInboundDtos) {
            if ( wiDto.getProductCode() != null) {
            Product product = productRepository.findById(wiDto.getProductCode()).orElse(null);

            if (employee != null && product != null) {
                //입고 상품 생성
                WarehouseInboundProduct warehouseProduct = WarehouseInboundProduct.createWarehouseProduct(product, wiDto.getInboundQuantity());
                //주문생성 -> 여러개의 입고물품에 대해서 1개의 주문 내역만 생성되도록 만들고 싶음.
                //for문 안에 들어가면 안됨.
                //WarehouseInbound 객체 안에 있는 inboundProducts 리스트 객체 안으로 들어가야함.
                warehouseInbound1.addOrderProduct(warehouseProduct);
                // 여기서 재고 업데이트
                updateStock(product, wiDto.getInboundQuantity());
            }
            }
            }

        List<WarehouseInboundProduct> inboundProducts = warehouseInbound1.getInboundProducts();

        WarehouseInbound warehouseInbound = WarehouseInbound.createInbound(employee,inboundProducts);
        wiRepository.save(warehouseInbound);


    }

    private void updateStock(Product product, int inboundQuantity) {
        product.setProductCnt(product.getProductCnt() + inboundQuantity);
        productRepository.save(product);
    }








}
