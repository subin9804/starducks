package org.kosta.starducks.logistic.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.commons.menus.MenuService;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.service.StoreService;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.logistic.dto.StoreInboundDto;
import org.kosta.starducks.logistic.dto.WarehouseInboundDto;
import org.kosta.starducks.logistic.entity.StoreInbound;
import org.kosta.starducks.logistic.entity.StoreInventory;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.kosta.starducks.logistic.service.StoreInboundService;
import org.kosta.starducks.logistic.service.StoreInventoryService;
import org.kosta.starducks.logistic.service.WarehouseInboundService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/logistic/inbound/store")
@RequiredArgsConstructor
@Slf4j
public class StoreInboundController {
    private final ProductService productService;
    private final StoreService storeService;
    private final StoreInboundService storeInboundService;
    private final EmpService empService;


    //재고 목록 조회
//    @GetMapping("/list1")
//    public String getAllInventories(Model m,
//                                 @PageableDefault(page = 0, size=3, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable,
//                                 @RequestParam(name="searchKeyword", required = false) String searchKeyword)
//    {
//        Page<StoreInventory> allInventory = null;
//        if(searchKeyword == null){
//            allInventory = productService.getAllProducts(pageable);
//
//        }
//        else{
//            allInventory = productService.productSearchList(searchKeyword,pageable);
//
//        }
//
//        int nowPage = allInventory.getPageable().getPageNumber()+1;
//        //pageable에서 넘어온 현재 페이지를 가져온다.
//        int startPage= Math.max(nowPage-4,1);
//        int endPage= Math.min(nowPage+5,allInventory.getTotalPages());
//
//        m.addAttribute("products", allInventory);
//        m.addAttribute("nowPage",nowPage);
//        m.addAttribute("startPage",startPage);
//        m.addAttribute("endPage",endPage);
//        return "logistic/InventoryList";
//    }


    @GetMapping("/list")
    public String getAllInbounds(Principal p,
                                 @PageableDefault(page = 0,size = 5) Pageable pageable,
                                 Model m)
//    ,@RequestParam(name = "bulkInboundCheckbox", required = false)Boolean bulkInboundCheckbox)
    {

        Page<StoreInbound> inbounds;

//        if (bulkInboundCheckbox != null && bulkInboundCheckbox) {
//            inbounds = warehouseInboundService.findRecentHighTotalPriceInbounds();
//        } else {
//            inbounds = warehouseInboundService.getAllInbounds();
//        }
        inbounds = storeInboundService.getAllInbounds(pageable);
        Employee emp = empService.getEmp(Long.parseLong(p.getName()));


        // 페이지 조건 생성

        int nowPage = inbounds.getPageable().getPageNumber() + 1;
        //pageable에서 넘어온 현재 페이지를 가져온다.
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, inbounds.getTotalPages());

        int totalPages = inbounds.getTotalPages();

        // 검색된 게 아무것도 없을 때 페이지 번호가 1이 보이게 설정
        if (totalPages == 0) {
            endPage = 1;
        }



        m.addAttribute("inbounds",inbounds);
        m.addAttribute("employee",emp);
        m.addAttribute("nowPage", nowPage);
        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("totalPages", totalPages);

        //m.addAttribute("checkbox",bulkInboundCheckbox);
        return "logistic/StoreInboundList";
    }

    @GetMapping("/info/{storeInboundId}")
    public String getInboundInfo(@PathVariable("storeInboundId") Long storeInboundId,
                                 Model m)
    {

        StoreInbound selectedInbound = storeInboundService.getInboundByInboundId(storeInboundId);
        m.addAttribute("inbound",selectedInbound);

//        Optional<Product> product = productService.getProduct(productCode);
//        Product product1 = product.get();
//        m.addAttribute("p", product1);

        return "logistic/StoreInboundDetail";



    }



    @GetMapping("/add")
    public String addOrder(Principal p,
                           Model m,
                           @Qualifier("pageable1")
                           @PageableDefault(page = 0, size = 100, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable1,
                           @Qualifier("pageable2")
                           @PageableDefault(page =0, size = 100, sort = "storeNo", direction = Sort.Direction.DESC) Pageable pageable2 )

    {


        Store byEmpId = storeService.findByEmpId(Long.parseLong(p.getName()));
        m.addAttribute("store",byEmpId);
        Page<Product> products = productService.getAllProducts(pageable1);
        m.addAttribute("products",products);

        return "logistic/StoreInboundForm";
    }




    @PostMapping("/add")
    public String addOrder(@RequestBody List<StoreInboundDto> storeInboundDtos)  {
        storeInboundService.addStoreInbound(storeInboundDtos);
        return "redirect:/logistic/inbound/store/list";
    }


}
