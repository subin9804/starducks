package org.kosta.starducks.logistic.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.hr.service.EmpService;

import org.kosta.starducks.logistic.dto.WarehouseOutboundDto;

import org.kosta.starducks.logistic.entity.WarehouseOutbound;
import org.kosta.starducks.logistic.service.WarehouseOutboundService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/logistic/outbound")
@RequiredArgsConstructor
@Slf4j
public class WarehouseOutboundController {
    private final ProductService productService;

    private final EmpService employeeService;
    private final WarehouseOutboundService warehouseOutboundService;


    @GetMapping("/warehouse/list")
    public String getAllOutbounds(Model m,@RequestParam(name = "bulkOutboundCheckbox", required = false)Boolean bulkOutboundCheckbox)
    {   log.info(String.valueOf(bulkOutboundCheckbox));

        List<WarehouseOutbound> outbounds;

        if (bulkOutboundCheckbox != null && bulkOutboundCheckbox) {
            outbounds = warehouseOutboundService.findRecentHighTotalPQOutbounds();
        } else {
            outbounds= warehouseOutboundService.getAllOutbounds();
        }

        m.addAttribute("outbounds", outbounds);
        m.addAttribute("checkbox",bulkOutboundCheckbox);



        return "logistic/OutboundList";
    }

    @GetMapping("/warehouse/info/{warehouseOutboundId}")
    public String getOutboundInfo(@PathVariable("warehouseOutboundId") Long warehouseOutboundId,
                                 Model m)
    {

        WarehouseOutbound selectedOutbound = warehouseOutboundService.getOutboundByOutboundId(warehouseOutboundId);
        m.addAttribute("outbound",selectedOutbound);

//

        return "logistic/OutboundDetail";



    }



    @GetMapping("/warehouse/add")
    public String addOrder(Model m,  @PageableDefault(page = 0, size = 100, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable)

    {

        m.addAttribute("employees",employeeService.getAllEmp());
        Page<Product> products = productService.getAllProducts(pageable);
        m.addAttribute("products",products);

        return "logistic/OutboundForm";
    }




    @PostMapping("/warehouse/add")
    public String addOrder(@RequestBody List<WarehouseOutboundDto> warehouseOutboundDtos)  {
        warehouseOutboundService.minusWarehouseOutbound(warehouseOutboundDtos);
        return "redirect:/logistic/outbound/warehouse/list";
    }


}
