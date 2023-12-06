package org.kosta.starducks.commons;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.repository.ForumPostRepository;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;
    private final ForumPostRepository forumPostRepository;
    private final PasswordEncoder passwordEncoder; //시큐리티 통과용 비밀번호 복호화

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
            emp.setPosition(Position.ROLE_EMPLOYEE);
            emp.setJoinDate(LocalDate.parse("2022-12-2"+i));
            emp.setPwd(passwordEncoder.encode("1q"));

            repository.saveAndFlush(emp);
        }

        // empId가 11이고, empPwd가 11을 만족하는 사원 생성
        Employee specificEmp = new Employee();
        specificEmp.setEmpId(11L); // empId를 11로 설정
        specificEmp.setStatus(false);
        specificEmp.setBirth(LocalDate.parse("2023-12-20"));
        specificEmp.setEmpTel("010-9999-9990");
        specificEmp.setGender("여");
        specificEmp.setEmail("sdf@Aasdf.com");
        specificEmp.setAddr("부천시");
        specificEmp.setEmpName("사원01");
        specificEmp.setPostNo("00025");
        specificEmp.setDAddr("용인시 오리구");
        specificEmp.setPosition(Position.ROLE_EMPLOYEE);
        specificEmp.setJoinDate(LocalDate.parse("2022-12-20"));
        specificEmp.setPwd(passwordEncoder.encode("11")); // 비밀번호를 "11"로 설정
        repository.saveAndFlush(specificEmp);


        //초기 vendor 데이터
        for(int i = 0; i < 5; i++) {
            Vendor vendor = new Vendor();
            vendor.setVendorName("거래처" + i);
            vendor.setVendorRegistNum("11"+i);
            vendor.setVendorRepreName("repre"+i);
            vendor.setVendorTelephone("010-9993-999"+i);
            vendor.setVendorStartDate(LocalDate.parse("2023-11-11"));
            vendor.setVendorAddress("용인시");

            vendorRepository.saveAndFlush(vendor);
        }

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

