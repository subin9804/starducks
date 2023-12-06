package org.kosta.starducks.logistic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.MenuService;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.generalAffairs.service.VendorService;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/logistic/warehouseinbound")
@RequiredArgsConstructor
public class WarehouseInboundController {


    private final ProductService productService;
    private final VendorService vendorService;
    private final EmpService employeeService;
    private final WarehouseInboundService warehouseInboundService;
    private final HttpServletRequest request;

    //재고 목록 조회
    @GetMapping("/list1")
    public String getAllInventories(Model m,
                                 @PageableDefault(page = 0, size=3, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(name="searchKeyword", required = false) String searchKeyword)
    {
        MenuService.commonProcess(request, m, "logistic");
        Page<Product> allInventory = null;
        if(searchKeyword == null){
            allInventory = productService.getAllProducts(pageable);

        }
        else{
            allInventory = productService.productSearchList(searchKeyword,pageable);

        }

        int nowPage = allInventory.getPageable().getPageNumber()+1;
        //pageable에서 넘어온 현재 페이지를 가져온다.
        int startPage= Math.max(nowPage-4,1);
        int endPage= Math.min(nowPage+5,allInventory.getTotalPages());

        m.addAttribute("products", allInventory);
        m.addAttribute("nowPage",nowPage);
        m.addAttribute("startPage",startPage);
        m.addAttribute("endPage",endPage);
        return "logistic/InventoryList";
    }


    @GetMapping("/list")
    public String getAllInbounds(Model m)
    {
        MenuService.commonProcess(request, m, "logistic");
        List<WarehouseInbound> allInbounds = warehouseInboundService.getAllInbounds();
        m.addAttribute("inbounds", allInbounds);
        return "logistic/InboundList";
    }

    @GetMapping("/info/{warehouseInboundId}")
    public String getInboundInfo(@PathVariable("warehouseInboundId") Long warehouseInboundId,
                                 Model m)
    {
        MenuService.commonProcess(request, m, "logistic");

        WarehouseInbound selectedInbound = warehouseInboundService.getInboundByInboundId(warehouseInboundId);
        m.addAttribute("inbound",selectedInbound);

//        Optional<Product> product = productService.getProduct(productCode);
//        Product product1 = product.get();
//        m.addAttribute("p", product1);

        return "logistic/InboundDetail";



    }



    @GetMapping("/add")
    public String addOrder(Model m,  @PageableDefault(page = 0, size = 100, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable)

    {
        MenuService.commonProcess(request, m, "logistic");
        //모든 getMappting에 넣어주기
        m.addAttribute("employees",employeeService.getAllEmp());
        Page<Product> products = productService.getAllProducts(pageable);
        m.addAttribute("products",products);

        return "logistic/InboundForm";
    }




    @PostMapping("/add")
    public String addOrder(@RequestBody List<WarehouseInboundDto> warehouseInboundDtos)  {
        warehouseInboundService.addWarehouseInbound(warehouseInboundDtos);
        return "redirect:/logistic/warehouseinbound/list";
    }


}
