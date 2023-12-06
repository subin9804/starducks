package org.kosta.starducks.commons;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.repository.ForumPostRepository;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {
//public class initData {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;
    private final ForumPostRepository forumPostRepository;
    private final ProductRepository productRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        //초기 사원 데이터
        for(int i = 0; i < 5; i++) {
            Employee emp = new Employee();
            emp.setEmpId((long) i);
            emp.setStatus(false);
            emp.setBirth(LocalDate.parse("2023-12-2"+i));
            emp.setEmpTel("010-9999-999"+i);
            emp.setGender("여");
            emp.setEmail("sdf@Aasdf.com");
            emp.setAddr("부천시");
            emp.setEmpName("사원0"+i);
            emp.setPostNo("00025");
            emp.setDAddr("용인시 오리구");
            emp.setPosition(Position.EMPLOYEE);
            emp.setJoinDate(LocalDate.parse("2022-12-2"+i));
            emp.setPwd("234jf");

            repository.saveAndFlush(emp);
        }

        //초기 vendor 데이터
//        for(int i = 0; i < 5; i++) {
//            Vendor vendor = new Vendor();
//            vendor.setVendorName("거래처" + i);
//            vendor.setVendorRegistNum("11"+i);
//            vendor.setVendorRepreName("repre"+i);
//            vendor.setVendorTelephone("010-9993-999"+i);
//            vendor.setVendorStartDate(LocalDate.parse("2023-11-11"));
//            vendor.setVendorAddress("용인시");
//
//            vendorRepository.saveAndFlush(vendor);
//        }

        Vendor vendor1 = new Vendor();
        vendor1.setVendorName("빈로스터리");
        vendor1.setVendorRegistNum("12458921");
        vendor1.setVendorRepreName("장총명");
        vendor1.setVendorTelephone("010-1212-3434");
        vendor1.setVendorStartDate(LocalDate.parse("2023-08-11"));
        vendor1.setVendorAddress("서울시 중구 장충동");
        vendorRepository.saveAndFlush(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setVendorName("대성산업");
        vendor2.setVendorRegistNum("46952029");
        vendor2.setVendorRepreName("김찬구");
        vendor2.setVendorTelephone("010-7122-8152");
        vendor2.setVendorStartDate(LocalDate.parse("2023-07-13"));
        vendor2.setVendorAddress("부산시 동래구 사직동");
        vendorRepository.saveAndFlush(vendor2);



        //초기 product 데이터
        Product product1 = new Product();
        product1.setProductSelling(true);
        product1.setProductPrice((long)75000);
        product1.setProductName("콜롬비아 부에나 비스타 게이샤");
        product1.setVendor(vendor1);
        product1.setProductCategory(ProductCategory.cate1);
        product1.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product1);

        Product product2 = new Product();
        product2.setProductSelling(true);
        product2.setProductPrice((long)15000);
        product2.setProductName("브라질 세하도 싱글원두");
        product2.setVendor(vendor1);
        product2.setProductCategory(ProductCategory.cate1);
        product2.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product2);

        Product product3 = new Product();
        product3.setProductSelling(false);
        product3.setProductPrice((long)29000);
        product3.setProductName("에티오피아 예가체프 코케허니 원두");
        product3.setVendor(vendor1);
        product3.setProductCategory(ProductCategory.cate1);
        product3.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product3);

        Product product4 = new Product();
        product4.setProductSelling(true);
        product4.setProductPrice((long)4200);
        product4.setProductName("14온스 PET 투명컵");
        product4.setVendor(vendor2);
        product4.setProductCategory(ProductCategory.cate2);
        product4.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product4);

        Product product5 = new Product();
        product5.setProductSelling(true);
        product5.setProductPrice((long)4000);
        product5.setProductName("9온스 PET 투명컵");
        product5.setVendor(vendor2);
        product5.setProductCategory(ProductCategory.cate2);
        product5.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product5);

        Product product6 = new Product();
        product6.setProductSelling(true);
        product6.setProductPrice((long)3900);
        product6.setProductName("16온스 흰색 무지 커피컵");
        product6.setVendor(vendor2);
        product6.setProductCategory(ProductCategory.cate2);
        product6.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product6);




        // 초기 게시글 데이터
        for (int i = 0; i < 33; i++) {
            ForumPost forumPost = new ForumPost();
            forumPost.setPostDate(LocalDateTime.now());
            forumPost.setPostTitle("제목" + i);
            forumPost.setPostContent("내용" + i);
            forumPost.setPostView(i);

            //공지사항 글 5개, 나머지 일반 게시글 더미 데이터
            if (i < 5) {
                forumPost.setPostNotice(true);
            } else {
                forumPost.setPostNotice(false);
            }

            forumPostRepository.saveAndFlush(forumPost);
        }
    }
}

