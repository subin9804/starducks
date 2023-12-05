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
import org.kosta.starducks.roles.Position;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {
//public class initData {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;
    private final ForumPostRepository forumPostRepository;
    private final DeptRepository deptRepository;

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
            emp.setPosition(Position.EMPLOYEE);
            emp.setJoinDate(LocalDate.parse("2022-12-2"+i));
            emp.setPwd("234jf");
            emp.setDept(deptRepository.findById(i).orElse(null));

            repository.saveAndFlush(emp);
        }

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
            forumPost.setPostTitle("제목"+i);
            forumPost.setPostContent("내용"+i);
            forumPost.setPostId((long) i);
            forumPost.setPostView(i);

            forumPostRepository.saveAndFlush(forumPost);
        }


    }
}

