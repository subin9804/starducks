package org.kosta.starducks.generalAffairs.controller;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.service.ProductService;
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

    @GetMapping("/list")
    public String getAllProducts(Model m)
    {
        List<Product> allProducts = productService.getAllProducts();
        m.addAttribute("products", allProducts);
        return "generalAffairs/layouts/ProductList";

    }

    @GetMapping("/info/{productId}")
    public String getPoductInfo(@PathVariable Long productId,
                                 Model m){

        Optional<Product> productById = productService.getProductById(productId);
        Product product = productById.get();
        m.addAttribute("p",product);
        return "generalAffairs/layouts/ProductInfo";
    }

    @PostMapping("/add")
    public Object addProduct(@Validated @RequestBody Product product){
        return productService.addProduct(product);

    }

//    @PostMapping("update")
//    public void updateProduct(@Validated @RequestBody Product product){
//        return productService
//    }



}
