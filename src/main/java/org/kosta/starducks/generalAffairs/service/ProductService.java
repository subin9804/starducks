package org.kosta.starducks.generalAffairs.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.dto.ProductUpdateDto;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${file.upload.path}")
    private String fileUploadPath;


    //품목 리스트 처리
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable)
    {
        return productRepository.findAll(pageable);
    }
    //품목 검색기능 포함한 리스트
    public Page<Product> productSearchList(String searchKeyword, Pageable pageable){
        return productRepository.findByProductNameContaining(searchKeyword, pageable);



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

    public void addProduct(Product product, MultipartFile file) throws Exception{

        //원래 파일이름 추출
        String origName = file.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        //확장자 추출
        String extension = origName.substring(origName.lastIndexOf("."));

        //uuid와 확장자 결합
        String savedName = uuid + extension;


//        //파일 저장할 경로 생성하기
//        String projectPath = System.getProperty("user.dir")+ "\\src\\main\\resources\\static\\images";
//
//        //랜덤으로 파일이름 만들기
//        UUID uuid = UUID.randomUUID();
//        String fileName = uuid+"_"+file.getOriginalFilename();

        //파일생성시 경로와 이름 설정하기
        File saveFile = new File(fileUploadPath, savedName);
        file.transferTo(saveFile);

        //DB에 파일 이름 및 경로 저장하기
        product.setFileName(savedName);
        product.setFilePath("/uploads/" + savedName);
        //product.setFilePath("/images/" + fileName);




        productRepository.save(product);
    }

    public String updateProduct (ProductUpdateDto productUpdateDto){


        Optional<Product> foundProduct = productRepository.findById(productUpdateDto.getProductCode());

        if (foundProduct.isPresent()) {
            Product product1 = foundProduct.get();
            product1.setProductCategory(productUpdateDto.getProductCategory());
            product1.setProductUnit(productUpdateDto.getProductUnit());
            product1.setProductPrice(productUpdateDto.getProductPrice());
            product1.setProductSelling(productUpdateDto.isProductSelling());
            productRepository.save(product1);

            return "수정완료";
        }
        else {
            // 만약 찾는 제품이 없을 경우에 대한 예외 처리 로직 추가
            return "해당 제품을 찾을 수 없습니다.";
    }
    }
    public String deleteProduct(Long productCode){
        productRepository.deleteById(productCode);
        return "삭제완료";
    }













}
