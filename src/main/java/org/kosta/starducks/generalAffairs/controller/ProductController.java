package org.kosta.starducks.generalAffairs.controller;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;
import org.kosta.starducks.generalAffairs.entity.Vendor;
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
        return "generalAffairs/ProductList";


    }

    @GetMapping("/info/{productCode}")
    public String getProductInfo(@PathVariable Long productCode,
                                 Model m)
    {
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
    public String addProduct(@ModelAttribute Product product, @RequestParam("vendorName") String vendorName) {
        Vendor vendorByName = vendorService.getVendorByName(vendorName);

        product.setVendor(vendorByName);
        productService.addProduct(product);
        return "redirect:/products/list";
    }


    @GetMapping("/update/{productCode}")
    public String updateProduct(@PathVariable Long productCode, Model m) {
        Optional<Product> product = productService.getProduct(productCode);

        if (product.isPresent()) {
            Product product1 = product.get();

        m.addAttribute("product", product1);
        m.addAttribute("productCategories",ProductCategory.values());
        m.addAttribute("productUnit", ProductUnit.values());
        m.addAttribute("productSelling", product1.isProductSelling());
        return "generalAffairs/ProductUpdate";
        }
        else{
            return "redirect:/products/list";
        }
    }

//    @PostMapping("/update/{productCode}")
//    public String updateProduct(@Validated @ModelAttribute ProductUpdateDto productUpdateDto) {
//        //Validated만 적어주면, 바인딩을 안해준다.
//        productService.updateProduct(productUpdateDto);
//
//        return "redirect:/product/list";
//    }



}
