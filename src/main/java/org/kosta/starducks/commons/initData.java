package org.kosta.starducks.commons;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.document.entity.DocForm;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.repository.ForumPostRepository;
import org.kosta.starducks.generalAffairs.entity.Product;
import org.kosta.starducks.generalAffairs.entity.ProductCategory;
import org.kosta.starducks.generalAffairs.entity.ProductUnit;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.ProductRepository;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.kosta.starducks.header.entity.ChatMessage;
import org.kosta.starducks.header.entity.ChatRoom;
import org.kosta.starducks.header.repository.ChatMessageRepository;
import org.kosta.starducks.header.repository.ChatRoomRepository;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.mypage.dto.Room;
import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.entity.ScheduleType;
import org.kosta.starducks.mypage.repository.ScheduleRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;
    private final ForumPostRepository forumPostRepository;
    private final DocFormRepository docFormRepository;
    private final ProductRepository productRepository;

    private final DeptRepository deptRepository;
    private final PasswordEncoder passwordEncoder; //시큐리티 통과용 비밀번호 복호화
    private final ScheduleRepository scheduleRepository;
    private final StoreRepository storeRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // 초기 부서 데이터
        for (int i = 0; i < 33; i++) {
            List<Department> depts = new ArrayList<>();
            depts.add(new Department(1, "사장실", "010-1111-1111"));
            depts.add(new Department(2, "재무부", "010-2222-2222"));
            depts.add(new Department(3, "총무부", "010-3333-3333"));
            depts.add(new Department(4, "물류유통부", "010-4444-4444"));
            depts.add(new Department(5, "인사부", "010-5555-5555"));

            deptRepository.saveAllAndFlush(depts);
        }

        // 초기 사원 데이터
        for (int i = 1; i < 5; i++) {
            Employee emp = new Employee();
            emp.setEmpId((long) i);
            emp.setStatus(false);
            emp.setBirth(LocalDate.parse("2023-12-2" + i));
            emp.setEmpTel("010-9999-999" + i);
            emp.setGender("woman");
            emp.setEmail("sdf@Aasdf.com");
            emp.setAddr("부천시");
            emp.setEmpName("사원0" + i);
            emp.setPostNo("00025");
            emp.setDAddr("용인시 오리구");
            emp.setPosition(Position.ROLE_EMPLOYEE);
            emp.setJoinDate(LocalDate.parse("2022-12-2" + i));
            emp.setPwd(passwordEncoder.encode("1"));
            emp.setDept(deptRepository.findById(i).orElse(null));

            repository.saveAndFlush(emp);
        }

        // 점검용 사원
        Employee specificEmp = new Employee();
        specificEmp.setEmpId(11L); // empId를 11로 설정
        specificEmp.setStatus(false);
        specificEmp.setBirth(LocalDate.parse("2023-12-20"));
        specificEmp.setEmpTel("010-9999-9990");
        specificEmp.setGender("man");
        specificEmp.setEmail("lhg0529@gmail.com");
        specificEmp.setAddr("수원시");
        specificEmp.setEmpName("이현기");
        specificEmp.setPostNo("00025");
        specificEmp.setDAddr("권선구");
        specificEmp.setPosition(Position.ROLE_STOREMANAGER);
        specificEmp.setJoinDate(LocalDate.parse("2022-12-20"));
        specificEmp.setDept(deptRepository.findById(2).orElse(null));
        specificEmp.setPwd(passwordEncoder.encode("1q")); // 비밀번호를 "1q"로 설정
        repository.saveAndFlush(specificEmp);

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
        product1.setProductPrice((long) 75000);
        product1.setProductName("콜롬비아 부에나 비스타 게이샤");
        product1.setVendor(vendor1);
        product1.setProductCategory(ProductCategory.cate1);
        product1.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product1);

        Product product2 = new Product();
        product2.setProductSelling(true);
        product2.setProductPrice((long) 15000);
        product2.setProductName("브라질 세하도 싱글원두");
        product2.setVendor(vendor1);
        product2.setProductCategory(ProductCategory.cate1);
        product2.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product2);

        Product product3 = new Product();
        product3.setProductSelling(false);
        product3.setProductPrice((long) 29000);
        product3.setProductName("에티오피아 예가체프 코케허니 원두");
        product3.setVendor(vendor1);
        product3.setProductCategory(ProductCategory.cate1);
        product3.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product3);

        Product product4 = new Product();
        product4.setProductSelling(true);
        product4.setProductPrice((long) 4200);
        product4.setProductName("14온스 PET 투명컵");
        product4.setVendor(vendor2);
        product4.setProductCategory(ProductCategory.cate2);
        product4.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product4);

        Product product5 = new Product();
        product5.setProductSelling(true);
        product5.setProductPrice((long) 4000);
        product5.setProductName("9온스 PET 투명컵");
        product5.setVendor(vendor2);
        product5.setProductCategory(ProductCategory.cate2);
        product5.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product5);

        Product product6 = new Product();
        product6.setProductSelling(true);
        product6.setProductPrice((long) 3900);
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
            forumPost.setEmployee(specificEmp);

            //공지사항 글 5개, 나머지 일반 게시글 더미 데이터
            forumPost.setPostNotice(i < 5);

            forumPostRepository.saveAndFlush(forumPost);
        }


        //문서 양식 데이터
        String[] formNames = {"기안서", "지출결의서", "발주서", "휴가신청서", "휴가취소사유서", "매출보고서", "재직증명서"};
        String[] formNamesEn = {"draft", "b", "c", "d", "e", "f", "empVerification"};
        for (int i = 1; i < 8; i++) {
            DocForm docForm = new DocForm();
            docForm.setFormCode("A0" + i);
            docForm.setFormName(formNames[i - 1]);
            docForm.setFormNameEn(formNamesEn[i - 1]);

            docFormRepository.saveAndFlush(docForm);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

//            Schedule 데이터 생성
            LocalDateTime[] startDates = {

                    LocalDateTime.parse("2023-12-06 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-10 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-10 00:00:00.000000", formatter)
            };

            LocalDateTime[] endDates = {
                    LocalDateTime.parse("2023-12-07 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-11 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-11 00:00:00.000000", formatter)
            };

            ScheduleType[] scheduleTypes = {ScheduleType.PERSONAL_SCHEDULE, ScheduleType.OFFICIAL_SCHEDULE, ScheduleType.PERSONAL_SCHEDULE};

            String[] titles = {"가가가가", "나나나나", "다다다다"};
            String[] notes = {"내용1", "내용2", "내용3"};

            Long[] empIds = {1L, 1L, 2L}; // Employee ID 배열

            for (int j = 0; j < 3; j++) {
                Schedule scheduleData = new Schedule();
                scheduleData.setScheNo((long) (j + 1));
                scheduleData.setScheTitle(titles[j]);
                scheduleData.setScheStartDate(startDates[j]);
                scheduleData.setScheEndDate(endDates[j]);
                scheduleData.setNotes(notes[j]);
                scheduleData.setScheduleType(scheduleTypes[j]);

                // Employee 객체 찾기
                Employee emp = repository.findById(empIds[j]).orElse(null);
                if (emp != null) {
                    scheduleData.setEmployee(emp); // Schedule 객체에 Employee 설정
                }

                scheduleRepository.saveAndFlush(scheduleData);
            }
        }

        // 초기 채팅 데이터 뭐야야야야
        for (int i = 0; i < 5; i++) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setRoomName("채팅방" + i);

//            ChatRoomRepository.save(chatRoom);
        }

        for (int i = 0; i < 5; i++) {



            ChatMessage msg = new ChatMessage();
//            msg.setChatRoom();
            msg.setMessage("내용임"+1);
            msg.setSender("이현기");
            msg.setReadStatus(false);

//            ChatMessageRepository.save(msg);
        }
    }
}