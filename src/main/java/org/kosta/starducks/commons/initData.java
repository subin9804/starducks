package org.kosta.starducks.commons;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.repository.ForumPostRepository;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.DeptRepository;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.mypage.repository.ScheduleRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;
    private final ForumPostRepository forumPostRepository;

    private final DeptRepository deptRepository;
    private final PasswordEncoder passwordEncoder; //시큐리티 통과용 비밀번호 복호화

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

        //초기 사원 데이터
        for(int i = 1; i < 5; i++) {
            Employee emp = new Employee();
            emp.setEmpId((long) i);
            emp.setStatus(false);
            emp.setBirth(LocalDate.parse("2023-12-2"+i));
            emp.setEmpTel("010-9999-999"+i);
            emp.setGender("woman");
            emp.setEmail("sdf@Aasdf.com");
            emp.setAddr("부천시");
            emp.setEmpName("사원0"+i);
            emp.setPostNo("00025");
            emp.setDAddr("용인시 오리구");
            emp.setPosition(Position.ROLE_EMPLOYEE);
            emp.setJoinDate(LocalDate.parse("2022-12-2"+i));
            emp.setPwd("234jf");
            emp.setDept(deptRepository.findById(i).orElse(null));

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
        specificEmp.setEmpName("사원11");
        specificEmp.setPostNo("00025");
        specificEmp.setDAddr("용인시 오리구");
        specificEmp.setPosition(Position.ROLE_EMPLOYEE);
        specificEmp.setJoinDate(LocalDate.parse("2022-12-20"));
        specificEmp.setDept(deptRepository.findById(2).orElse(null));
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

