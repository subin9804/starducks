package org.kosta.starducks.generalAffairs.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.dto.ProductUpdateDto;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    @Transactional(readOnly = true)
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(Long productCode){
        Optional<Product> byId = productRepository.findById(productCode);
        return byId;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProductsByProductName (String productName){
        return productRepository.findByProductName(productName);
    }

    public Product addProduct (Product product){
        return productRepository.save(product);
    }

    public String updateProduct (ProductUpdateDto productUpdateDto){


        Optional<Product> foundProduct = productRepository.findById(productUpdateDto.getProductCode());
        Product product1 = foundProduct.get();
        product1.setProductName(productUpdateDto.getProductName());
        product1.setProductUnit(productUpdateDto.getProductUnit());
        product1.setProductPrice(productUpdateDto.getProductPrice());

        productRepository.save(product1);

        return "수정완료";
    }
    public String deleteProduct(Long productCode){
        productRepository.deleteById(productCode);
        return "삭제완료";
    }













}
