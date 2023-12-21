package org.kosta.starducks.generalAffairs.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.menus.MenuService;
import org.kosta.starducks.generalAffairs.dto.ProductUpdateDto;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/general/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final VendorService vendorService;
    private final HttpServletRequest request;


    @GetMapping("/list")
    public String getAllProducts(Model m,
                                 @PageableDefault(page = 0, size = 3, sort = "productCode", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {
        Page<Product> allProducts = null;
        if (searchKeyword == null) {
            allProducts = productService.getAllProducts(pageable);

        } else {
            allProducts = productService.productSearchList(searchKeyword, pageable);

        }

        int nowPage = allProducts.getPageable().getPageNumber() + 1;
        //pageable에서 넘어온 현재 페이지를 가져온다.
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, allProducts.getTotalPages());

        m.addAttribute("products", allProducts);
        m.addAttribute("nowPage", nowPage);
        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        return "generalAffairs/ProductList";
    }

    @GetMapping("/info/{productCode}")
    public String getProductInfo(@PathVariable("productCode") Long productCode,
                                 Model m) {
        Optional<Product> product = productService.getProduct(productCode);
        Product product1 = product.get();
        m.addAttribute("p", product1);
        return "generalAffairs/ProductDetail";


    }

    @GetMapping("/add")
    public String addProduct(Model m) {
        m.addAttribute("productCategories", ProductCategory.values());
        m.addAttribute("productUnit", ProductUnit.values());

        List<String> vendorNames = vendorService.getAllVendorNames();
        m.addAttribute("vendorNames", vendorNames);

        return "generalAffairs/ProductForm";
    }


    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, @RequestParam("vendorName") String vendorName, @RequestParam("file") MultipartFile file) throws Exception {
        Vendor vendorByName = vendorService.getVendorByName(vendorName);

        product.setVendor(vendorByName);
        productService.addProduct(product, file);
        return "redirect:/general/products/list";
    }


    @GetMapping("/update/{productCode}")
    public String updateProduct(@PathVariable("productCode") Long productCode, Model m) {
        Optional<Product> product = productService.getProduct(productCode);

        if (product.isPresent()) {
            Product product1 = product.get();

            m.addAttribute("product", product1);
            m.addAttribute("productCategories", ProductCategory.values());
            m.addAttribute("productUnit", ProductUnit.values());
            m.addAttribute("productSelling", product1.isProductSelling());
            return "generalAffairs/ProductUpdate";
        } else {
            return "redirect:/general/products/list";
        }
    }

    @PostMapping("/update/{productCode}")
    public String updateProduct(@Validated @ModelAttribute ProductUpdateDto productUpdateDto,
                                @PathVariable("productCode") Long productCode) {
        //Validated만 적어주면, 유효하지 않은 값 바인딩을 안해준다.

        productService.updateProduct(productUpdateDto);

        return "redirect:/general/products/list";
    }

//    @PostMapping("/update/{productCode}")
//    public String updateProduct(@Validated @ModelAttribute ProductUpdateDto productUpdateDto) {
//        //Validated만 적어주면, 바인딩을 안해준다.
//        productService.updateProduct(productUpdateDto);
//
//        return "redirect:/product/list";
//    }


}
