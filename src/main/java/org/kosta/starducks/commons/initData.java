//package org.kosta.starducks.commons;
//
//import lombok.RequiredArgsConstructor;
//import org.kosta.starducks.hr.entity.EmpEntity;
//import org.kosta.starducks.hr.repository.EmpRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//public class initData implements CommandLineRunner{
//
//    private final EmpRepository repository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 초기 데이터 입력 로직
//        for(int i = 0; i < 5; i++) {
//            EmpEntity emp = EmpEntity.builder()
//                    .status(false)
//                    .birth(LocalDate.parse("2023-12-21"))
//                    .empTel("010-9999-999" + i)
//                    .gender("여")
//                    .email("sdf@Aasdf.com")
//                    .addr("부천시")
//                    .dAddr("부천시소사구")
//                    .joinDate(LocalDate.parse("2022-12-11"))
//                    .pwd("234jf")
//                    .build();
//
//            repository.save(emp);
//        }
//
//
//    }
//}

