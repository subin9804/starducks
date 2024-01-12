package org.kosta.starducks.logistic.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.generalAffairs.dto.ProductStockUpdateDto;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.logistic.dto.StockUpdateDto;
import org.kosta.starducks.logistic.entity.StoreInventory;
import org.kosta.starducks.logistic.service.StoreInventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/logistic/stock")
@RequiredArgsConstructor
@Slf4j

public class StockController {

    private final ProductService productService;
    private final StoreInventoryService storeInventoryService;
    private final HttpServletRequest request;

    @GetMapping("/warehouse/list")
    public String getAllInventories(Model m,
                                    @PageableDefault(page = 0, size = 5, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {

        Page<Product> allInventory = null;
        if (searchKeyword == null) {
            allInventory = productService.getAllProducts(pageable);

        } else {
            allInventory = productService.productSearchList(searchKeyword, pageable);

        }


        // 페이지 조건 생성

        int nowPage = allInventory.getPageable().getPageNumber() + 1;
        //pageable에서 넘어온 현재 페이지를 가져온다.
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, allInventory.getTotalPages());

        int totalPages = allInventory.getTotalPages();

        // 검색된 게 아무것도 없을 때 페이지 번호가 1이 보이게 설정
        if (totalPages == 0) {
            endPage = 1;
        }

        m.addAttribute("products", allInventory);
        m.addAttribute("nowPage", nowPage);
        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("totalPages", totalPages);
        return "logistic/InventoryList";
    }


    @GetMapping("/warehouse/info/{code}")
    public String getwInventoryInfo(
            @PathVariable("code") Long productCode,
            Model m) {
        Optional<Product> product = productService.getProduct(productCode);
        m.addAttribute("inventory", product.get());

        return "logistic/InventoryDetail";
    }

    @GetMapping("/warehouse/update/{code}")
    public String updatewStock(@PathVariable("code") Long productCode,
                               Model m) {
        Optional<Product> product = productService.getProduct(productCode);
        if (product.isPresent()) {
            Product product1 = product.get();
            m.addAttribute("inventory", product1);

            return "logistic/InventoryUpdate";
        } else {
            return "redirect:/logistic/stock/warehouse/list";
        }
    }


    @PostMapping("/warehouse/update/{productCode}")
    public String updatewStock(@ModelAttribute ProductStockUpdateDto productStockUpdateDto) {
        productService.updateProductStock(productStockUpdateDto);
        return "redirect:/logistic/stock/warehouse/list";
    }


    @GetMapping("/store/list")
    public String getAllStoreInventories(Model m,
                                    @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {



        Page<StoreInventory> allStoreInventories = null;
        if (searchKeyword == null) {
            allStoreInventories = storeInventoryService.getAllStoreInventories(pageable);

        } else {
            allStoreInventories = storeInventoryService.storeInventorySearchList(searchKeyword, pageable);

        }


        // 페이지 조건 생성

        int nowPage = allStoreInventories.getPageable().getPageNumber() + 1;
        //pageable에서 넘어온 현재 페이지를 가져온다.
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, allStoreInventories.getTotalPages());

        int totalPages = allStoreInventories.getTotalPages();

        // 검색된 게 아무것도 없을 때 페이지 번호가 1이 보이게 설정
        if (totalPages == 0) {
            endPage = 1;
        }




        List<StoreInventory> allInventories = storeInventoryService.getAllInventories();

        m.addAttribute("inventories", allStoreInventories);
        m.addAttribute("nowPage", nowPage);
        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("totalPages", totalPages);


        return "logistic/StoreInventoryList";

    }


    @GetMapping("/store/info/{productCode}/{storeNo}")
    public String getsInventoryInfo(@PathVariable("storeNo") Long storeNo,
                                    @PathVariable("productCode") Long productCode,
                                    Model m) {
        StoreInventory inventoryByCodeAndNo = storeInventoryService.getInventoryByNoAndCode(storeNo, productCode);
        m.addAttribute("inventory", inventoryByCodeAndNo);

        return "logistic/StoreInventoryDetail";
    }

    @GetMapping("/store/update/{code}/{no}")
    public String updatesStock(@PathVariable("no") Long storeNo,
                               @PathVariable("code") Long productCode,
                               Model m) {

        StoreInventory inventoryByNoAndCode = storeInventoryService.getInventoryByNoAndCode(storeNo, productCode);
        m.addAttribute("inventory", inventoryByNoAndCode);

        return "logistic/StoreInventoryUpdate";

    }


    @PostMapping("/store/update/{productCode}/{storeNo}")
    public String updatesStock(@ModelAttribute StockUpdateDto stockUpdateDto) {
        storeInventoryService.updateStock(stockUpdateDto);
        return "redirect:/logistic/stock/store/list";
    }


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


}
