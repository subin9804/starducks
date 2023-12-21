package org.kosta.starducks.logistic.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.logistic.dto.WarehouseOutboundDto;
import org.kosta.starducks.logistic.entity.WarehouseOutbound;
import org.kosta.starducks.logistic.entity.WarehouseOutboundProduct;
import org.kosta.starducks.logistic.repository.WarehouseOutboundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseOutboundService {


    private final WarehouseOutboundRepository woRepository;
    private final EmpRepository empRepository;
    private final ProductRepository productRepository;

    //모든 입고 사항 가져오기
    public List<WarehouseOutbound> getAllOutbounds(){
        return woRepository.findAll();
    }
    public List<WarehouseOutbound> findRecentHighTotalPQOutbounds() {
        // 위에서 작성한 쿼리 메서드 활용
        return woRepository.findRecentHighTotalPriceOutbounds();
    }
    public List<WarehouseOutbound> getAllOutboundsByEmpId(Long empId){
        return woRepository.findWarehouseOutboundsByEmployee_EmpId(empId);
    }
    public WarehouseOutbound getOutboundByOutboundId(Long outboundId){

        return woRepository.findById(outboundId).get();
    }

    public void minusWarehouseOutbound(List<WarehouseOutboundDto> warehouseOutboundDtos) {//userid,[productId,orderQuantity]


        WarehouseOutbound warehouseOutbound1 = new WarehouseOutbound();

        Employee employee = empRepository.findById(warehouseOutboundDtos.get(0).getEmpId()).get();


        for (WarehouseOutboundDto woDto : warehouseOutboundDtos) {
            if ( woDto.getProductCode() != null) {
            Product product = productRepository.findById(woDto.getProductCode()).orElse(null);

            if (employee != null && product != null) {
                //입고 상품 생성
                WarehouseOutboundProduct warehouseProduct = WarehouseOutboundProduct.createWarehouseProduct(product, woDto.getOutboundQuantity());
                //주문생성 -> 여러개의 입고물품에 대해서 1개의 주문 내역만 생성되도록 만들고 싶음.
                //for문 안에 들어가면 안됨.
                //WarehouseInbound 객체 안에 있는 inboundProducts 리스트 객체 안으로 들어가야함.
                warehouseOutbound1.addOrderProduct(warehouseProduct);
                // 여기서 재고 업데이트
                updateStock(product, woDto.getOutboundQuantity());
            }
            }
            }

        List<WarehouseOutboundProduct> outboundProducts = warehouseOutbound1.getOutboundProducts();

        WarehouseOutbound warehouseOutbound = WarehouseOutbound.createOutbound(employee,outboundProducts);
        woRepository.save(warehouseOutbound);


    }

    private void updateStock(Product product, int outboundQuantity) {
        product.setProductCnt(product.getProductCnt()-outboundQuantity);
        productRepository.save(product);
    }








}
