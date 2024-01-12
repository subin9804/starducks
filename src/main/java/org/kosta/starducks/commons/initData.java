package org.kosta.starducks.commons;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.notify.service.NotifyService;
import org.kosta.starducks.document.entity.DocForm;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.Store;
import org.kosta.starducks.fina.entity.StoreOperationalYn;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.fina.repository.StoreRepository;
import org.kosta.starducks.fina.service.StoreService;
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
import org.kosta.starducks.header.entity.ChatRoomEmp;
import org.kosta.starducks.header.repository.ChatMessageRepository;
import org.kosta.starducks.header.repository.ChatRoomEmpRepository;
import org.kosta.starducks.header.repository.ChatRoomRepository;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.kosta.starducks.hr.repository.EmpRepository;
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
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;
    private final ForumPostRepository forumPostRepository;
    private final DocFormRepository docFormRepository;
    private final ProductRepository productRepository;
    private final ChatRoomEmpRepository chatRoomEmpRepository;

    private final DeptRepository deptRepository;
    private final PasswordEncoder passwordEncoder; //시큐리티 통과용 비밀번호 복호화
    private final ScheduleRepository scheduleRepository;
    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final NotifyService notifyService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //배포 서버 JVM 시간 설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        // 초기 부서 데이터
        List<Department> depts = new ArrayList<>();
        depts.add(new Department(1, "사장실", "boss", "010-1111-1111"));
        depts.add(new Department(2, "재무부", "fina", "010-2222-2222"));
        depts.add(new Department(3, "총무부", "general", "010-3333-3333"));
        depts.add(new Department(4, "물류유통부", "logistic", "010-4444-4444"));
        depts.add(new Department(5, "인사부", "hr", "010-5555-5555"));

        deptRepository.saveAllAndFlush(depts);

        // 초기 사원 데이터
        List<Employee> emps = new ArrayList<>();
        emps.add(Employee.builder().empId(1L).empName("최필립").birth(LocalDate.parse("1985-11-05"))
                .joinDate(LocalDate.parse("2017-12-25")).email("lib@monster.com").dept(depts.get(0))
                .gender("man").position(Position.ROLE_DEPTLEADER).pwd(passwordEncoder.encode("1")).status(false).postNo("08374").addr("서울 구로구 오리로 1063-2")
                .dAddr("길동빌딩 302호").empTel("010-2344-2345").build());
        emps.add(Employee.builder().empId(2L).empName("배수지").birth(LocalDate.parse("1991-01-25"))
                .joinDate(LocalDate.parse("2013-12-25")).email("suzi@monster.com").dept(depts.get(1))
                .gender("woman").position(Position.ROLE_EMPLOYEE).pwd(passwordEncoder.encode("1")).status(false).postNo("056936").addr("경기 남양주시 오남읍 양지로대대울1길 4")
                .dAddr("오리오피스텔 1102호").empTel("010-2644-3457").build());
        emps.add(Employee.builder().empId(3L).empName("이두나").birth(LocalDate.parse("1995-05-30"))
                .joinDate(LocalDate.parse("2015-12-25")).email("duna@monster.com").dept(depts.get(2))
                .gender("woman").position(Position.ROLE_TEAMLEADER).pwd(passwordEncoder.encode("1")).status(false).postNo("08853").addr("서울 서초구 과천대로 786")
                .dAddr("두나팰리스 A동 1023호").empTel("010-1246-2241").build());
        emps.add(Employee.builder().empId(4L).empName("김길동").birth(LocalDate.parse("1999-04-19"))
                .joinDate(LocalDate.parse("2021-12-25")).email("kimgil@monster.com").dept(depts.get(3))
                .gender("man").position(Position.ROLE_EMPLOYEE).pwd(passwordEncoder.encode("1")).status(false).postNo("02866").addr("서울 강서구 강서로 375-7")
                .dAddr("푸르지오 2차 305동 201호").empTel("010-6789-1384").build());
        emps.add(Employee.builder().empId(5L).empName("최사원").birth(LocalDate.parse("1996-12-02"))
                .joinDate(LocalDate.parse("2022-12-25")).email("sawon@monster.com").dept(depts.get(4))
                .gender("man").position(Position.ROLE_EMPLOYEE).pwd(passwordEncoder.encode("1")).status(false).postNo("07316").addr("부산 강서구 가락대로 197-1")
                .dAddr("오리빌라 102호").empTel("010-2347-0663").build());

        repository.saveAllAndFlush(emps);

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
        specificEmp.setDept(deptRepository.findById(4).orElse(null));
        specificEmp.setPwd(passwordEncoder.encode("1q")); // 비밀번호를 "1q"로 설정
        repository.saveAndFlush(specificEmp);


//        마스터 계정
        Employee masterEmp = new Employee();
        masterEmp.setEmpId(1004L); // empId를 11로 설정
        masterEmp.setStatus(false);
        masterEmp.setBirth(LocalDate.parse("2023-08-09"));
        masterEmp.setEmpTel("010-1004-1004");
        masterEmp.setGender("woman");
        masterEmp.setEmail("lhg0529@gmail.com");
        masterEmp.setAddr("성남시");
        masterEmp.setEmpName("나보스");
        masterEmp.setPostNo("00025");
        masterEmp.setDAddr("분당구");
        masterEmp.setPosition(Position.ROLE_BOSS);
        masterEmp.setJoinDate(LocalDate.parse("2022-12-20"));
        masterEmp.setDept(deptRepository.findById(1).orElse(null));
        masterEmp.setPwd(passwordEncoder.encode("1004"));
        repository.saveAndFlush(masterEmp);

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
        vendor1.setVendorId(1);
        vendor1.setVendorName("빈로스터리");
        vendor1.setVendorBusinessSector(VendorBusinessSector.COFFEEBEANSUPPLIERS);
        vendor1.setVendorRegistNum("124-58-92123");
        vendor1.setVendorRepreName("장총명");
        vendor1.setVendorTelephone("010-1212-3434");
        vendor1.setVendorStartDate(LocalDate.parse("2023-08-11"));
        vendor1.setVendorAddress("서울시 중구 장충동");
        vendor1.setContractStatus(ContractStatus.CONTRACT_ACTIVE);
        vendorRepository.saveAndFlush(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setVendorId(2);
        vendor2.setVendorName("대성산업");
        vendor2.setVendorBusinessSector(VendorBusinessSector.FARMINGANDCULTIVATION);
        vendor2.setVendorRegistNum("469-52-02963");
        vendor2.setVendorRepreName("김찬구");
        vendor2.setVendorTelephone("010-7122-8152");
        vendor2.setVendorStartDate(LocalDate.parse("2023-07-13"));
        vendor2.setVendorAddress("부산시 동래구 사직동");
        vendor2.setContractStatus(ContractStatus.CONTRACT_STOPPED);
        vendorRepository.saveAndFlush(vendor2);


//        //초기 product 데이터
        Product product1 = new Product();
        product1.setProductCode(1L);
        product1.setProductSelling(true);
        product1.setProductPrice((long) 75000);
        product1.setProductName("콜롬비아 부에나 비스타 게이샤");
        product1.setVendor(vendor1);
        product1.setProductCategory(ProductCategory.cate1);
        product1.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product1);

        Product product2 = new Product();
        product2.setProductCode(2L);
        product2.setProductSelling(true);
        product2.setProductPrice((long) 15000);
        product2.setProductName("브라질 세하도 싱글원두");
        product2.setVendor(vendor1);
        product2.setProductCategory(ProductCategory.cate1);
        product2.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product2);

        Product product3 = new Product();
        product3.setProductCode(3L);
        product3.setProductSelling(false);
        product3.setProductPrice((long) 29000);
        product3.setProductName("에티오피아 예가체프 코케허니 원두");
        product3.setVendor(vendor1);
        product3.setProductCategory(ProductCategory.cate1);
        product3.setProductUnit(ProductUnit.KG);
        productRepository.saveAndFlush(product3);

        Product product4 = new Product();
        product4.setProductCode(4L);
        product4.setProductSelling(true);
        product4.setProductPrice((long) 4200);
        product4.setProductName("14온스 PET 투명컵");
        product4.setVendor(vendor2);
        product4.setProductCategory(ProductCategory.cate2);
        product4.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product4);

        Product product5 = new Product();
        product5.setProductCode(5L);
        product5.setProductSelling(true);
        product5.setProductPrice((long) 4000);
        product5.setProductName("9온스 PET 투명컵");
        product5.setVendor(vendor2);
        product5.setProductCategory(ProductCategory.cate2);
        product5.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product5);

        Product product6 = new Product();
        product6.setProductCode(6L);
        product6.setProductSelling(true);
        product6.setProductPrice((long) 3900);
        product6.setProductName("16온스 흰색 무지 커피컵");
        product6.setVendor(vendor2);
        product6.setProductCategory(ProductCategory.cate2);
        product6.setProductUnit(ProductUnit.HUNDRED_EA);
        productRepository.saveAndFlush(product6);

        //초기 지점 데이터

        Store store = new Store();
        store.setStoreNo(1L);
        store.setEmployee(specificEmp);
        store.setStoreName("미금역점");
        store.setBusinessNum(1148600675L);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2023-08-08", dateFormat);
        store.setStoreOpenDate(date);
        store.setAddNo("05704");
        store.setStoreAddr("경기도 성남시 분당구 ");
        store.setStoreDetailAddr("금곡동 168");
        store.setStoreOperationalYn(StoreOperationalYn.storeOperationalY);
        storeService.createStore(store);

        // 초기 게시글 데이터
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 2; i++) {
            List<ForumPost> posts = new ArrayList<>();
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2023-12-07 00:00:00", dtf)).postTitle("건의사항과 개선 아이디어를 모아봅시다")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(1)).postNotice(true).build());
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2023-12-07 00:00:00", dtf)).postTitle("회사 이벤트 및 축하 메시지 모음")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(2)).postNotice(true).build());
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2023-12-03 00:00:00", dtf)).postTitle("프로젝트 기술 리뷰 및 피드백 모집")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(3)).postNotice(false).build());
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2023-12-20 00:00:00", dtf)).postTitle("팀 전체 회식 아이디어 모집")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(4)).postNotice(false).build());
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2022-11-30 00:00:00", dtf)).postTitle("최근 경험한 기술적인 도전과 해결")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(0)).postNotice(false).build());
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2021-10-02 00:00:00", dtf)).postTitle("회사 문화 개선을 위한 아이디어 제안")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(1)).postNotice(false).build());
            posts.add(ForumPost.builder().postDate(LocalDateTime.parse("2022-08-17 00:00:00", dtf)).postTitle("2024 사내기숙사 입주자 모집")
                    .postContent("모집양식은 각 부서 팀장에게 문의바랍니다.").employee(emps.get(3)).postNotice(false).build());

            forumPostRepository.saveAllAndFlush(posts);
        }

        //문서 양식 데이터
        String[] formNames = {"기안서", "지출결의서", "발주서", "휴가신청서", "휴가취소사유서", "매출보고서", "재직증명서"};
        String[] formNamesEn = {"draft", "b", "orderForm", "d", "e", "f", "employmentLetter"};
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
                    LocalDateTime.parse("2023-12-10 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-06 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-10 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-10 00:00:00.000000", formatter)
            };

            LocalDateTime[] endDates = {
                    LocalDateTime.parse("2023-12-07 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-11 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-11 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-07 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-11 00:00:00.000000", formatter),
                    LocalDateTime.parse("2023-12-11 00:00:00.000000", formatter)
            };

            ScheduleType[] scheduleTypes = {ScheduleType.PERSONAL_SCHEDULE, ScheduleType.OFFICIAL_SCHEDULE, ScheduleType.PERSONAL_SCHEDULE, ScheduleType.PERSONAL_SCHEDULE, ScheduleType.PERSONAL_SCHEDULE, ScheduleType.PERSONAL_SCHEDULE};

            String[] titles = {"팀 회식", "마케팅 강연", "점심약속", "팀 회식", "마케팅 강연", "점심약속"};
            String[] notes = {"회식", "강연", "약속", "회식", "강연", "약속"};

            Long[] empIds = {1L, 1L, 2L, 3L, 3L, 3L}; // Employee ID 배열

            for (int j = 0; j < 6; j++) {
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

        // 초기 채팅 데이터
        // 사번이 1인 사원과 11인 사원을 가져옵니다.
        Employee emp1 = repository.findById(1L).orElse(null);
        Employee emp2 = repository.findById(2L).orElse(null);
        Employee emp11 = repository.findById(11L).orElse(null);

        if (emp1 != null && emp11 != null) {
            // 채팅방 생성

            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setRoomName("더미 채팅방 이름");
            chatRoom = chatRoomRepository.save(chatRoom);

            // 채팅방 참여자 연결
            ChatRoomEmp emp1ChatRoom = new ChatRoomEmp(chatRoom, emp1);
            chatRoomEmpRepository.save(emp1ChatRoom);
            ChatRoomEmp emp11ChatRoom = new ChatRoomEmp(chatRoom, emp11);
            chatRoomEmpRepository.save(emp11ChatRoom);

            // 메시지 생성
            for (int i = 0; i < 3; i++) {
                ChatMessage msg = new ChatMessage();
                msg.setChatRoom(chatRoom);
                msg.setMessage("메시지 내용 " + i);
                msg.setSender(emp1); // 사번이 1인 사원이 메시지를 보냈다고 가정
                msg.setReadStatus(false);
                chatMessageRepository.save(msg);
            }
        }

        // 사원 11과 사원 2가 대화하는 더미 데이터 생성
        if (emp2 != null && emp11 != null) {
            // 새 채팅방 생성
            ChatRoom chatRoom2 = new ChatRoom();
            chatRoom2.setRoomName("사원 11과 사원 2의 채팅방");
            chatRoom2 = chatRoomRepository.save(chatRoom2);

            // 채팅방 참여자 연결
            ChatRoomEmp emp2ChatRoom = new ChatRoomEmp(chatRoom2, emp2);
            chatRoomEmpRepository.save(emp2ChatRoom);
            ChatRoomEmp emp11ChatRoom2 = new ChatRoomEmp(chatRoom2, emp11);
            chatRoomEmpRepository.save(emp11ChatRoom2);

            // 메시지 생성
            for (int i = 0; i < 3; i++) {
                ChatMessage msg = new ChatMessage();
                msg.setChatRoom(chatRoom2);
                msg.setMessage("밥 어떤거 드세요? " + i);
                msg.setSender(emp11); // 사번이 11인 사원이 메시지를 보냈다고 가정
                msg.setReadStatus(false);
                chatMessageRepository.save(msg);
            }
        }
    }
}
