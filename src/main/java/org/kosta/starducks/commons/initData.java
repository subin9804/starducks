package org.kosta.starducks.commons;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationListener<ApplicationReadyEvent> {
//public class initData {

    private final EmpRepository repository;
    private final VendorRepository vendorRepository;

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
    }
}

