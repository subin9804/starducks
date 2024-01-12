package org.kosta.starducks.logistic.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.logistic.dto.WarehouseInboundDto;
import org.kosta.starducks.logistic.entity.WarehouseInbound;
import org.kosta.starducks.logistic.service.WarehouseInboundService;
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
@RequestMapping("/logistic/inbound")
@RequiredArgsConstructor
@Slf4j
public class WarehouseInboundController {
    private final ProductService productService;
    private final VendorService vendorService;
    private final EmpService employeeService;
    private final WarehouseInboundService warehouseInboundService;
    private final HttpServletRequest request;

    //재고 목록 조회
//    @GetMapping("/warehouse/list1")
//    public String getAllInventories(Model m,
//                                 @PageableDefault(page = 0, size=3, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable,
//                                 @RequestParam(name="searchKeyword", required = false) String searchKeyword)
//    {
//        MenuService.commonProcess(request, m, "logistic");
//        Page<Product> allInventory = null;
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


    @GetMapping("/warehouse/list")
    public String getAllInbounds(Model m,
                                 @PageableDefault(page = 0, size = 5) Pageable pageable,
                                 @RequestParam(name = "bulkInboundCheckbox", required = false) Boolean bulkInboundCheckbox) {

        log.info(String.valueOf(bulkInboundCheckbox));


        Page<WarehouseInbound> inbounds;

        if (bulkInboundCheckbox != null && bulkInboundCheckbox) {
            inbounds = warehouseInboundService.findRecentHighTotalAmountAndPrice(pageable);
        } else {
            inbounds = warehouseInboundService.getAllWarehouseInbounds(pageable);
        }


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

        m.addAttribute("inbounds", inbounds);
        m.addAttribute("checkbox", bulkInboundCheckbox);
        m.addAttribute("nowPage", nowPage);
        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("totalPages", totalPages);


        return "logistic/InboundList";
    }


    @GetMapping("/warehouse/info/{warehouseInboundId}")
    public String getInboundInfo(@PathVariable("warehouseInboundId") Long warehouseInboundId, Model m) {

        WarehouseInbound selectedInbound = warehouseInboundService.getInboundByInboundId(warehouseInboundId);
        m.addAttribute("inbound", selectedInbound);

//        Optional<Product> product = productService.getProduct(productCode);
//        Product product1 = product.get();
//        m.addAttribute("p", product1);

        return "logistic/InboundDetail";
    }


    @GetMapping("/warehouse/add")
    public String addOrder(Principal p,
                           Model m, @PageableDefault(page = 0, size = 100, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable) {


        Employee emp = employeeService.getEmp(Long.parseLong(p.getName()));
        m.addAttribute("employee", emp);

        Page<Product> products = productService.getAllProducts(pageable);
        m.addAttribute("products", products);

        return "logistic/InboundForm";
    }


    @PostMapping("/warehouse/add")
    public String addOrder(@RequestBody List<WarehouseInboundDto> warehouseInboundDtos) {
        warehouseInboundService.addWarehouseInbound(warehouseInboundDtos);
        return "redirect:/logistic/inbound/warehouse/list";
    }


}
