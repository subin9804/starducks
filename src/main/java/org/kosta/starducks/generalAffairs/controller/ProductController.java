package org.kosta.starducks.generalAffairs.controller;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;
import org.kosta.starducks.generalAffairs.service.ProductService;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    private final VendorService vendorService;


    @GetMapping("/list")
    public String getAllProducts(Model m)
    {
        List<Product> allProducts = productService.getAllProducts();
        m.addAttribute("products", allProducts);
        return "generalAffairs/layouts/ProductList";


    }

    @GetMapping("/info/{productCode}")
    public String getProductInfo(@PathVariable Long productCode,
                                 Model m)
    {
        Optional<Product> product = productService.getProduct(productCode);
        Product product1 = product.get();
        m.addAttribute("p", product1);
        return "generalAffairs/layouts/ProductDetail";


    }

    @GetMapping("/add")
    public String addProduct(Model m) {
        m.addAttribute("productCategories", ProductCategory.values());
        m.addAttribute("productUnit", ProductUnit.values());

        List<String> vendorNames = vendorService.getAllVendorNames();
        m.addAttribute("vendorNames", vendorNames);

        return "generalAffairs/layouts/ProductForm";
    }

//    @PostMapping("/add")
//    public Object addProduct(@Validated @RequestBody Product product){
//        return productService.addProduct(product);
//
//    }


}
