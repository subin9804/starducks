package org.kosta.starducks.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.kosta.starducks.roles.Position;

import java.time.LocalDate;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "emp_entity")
public class EmpEntity {

    @Id
    @GeneratedValue
    private Long empId; // 사원번호

    private String empName;     // 사원이름

    private LocalDate birth;    // 생년월일
    private String gender;  // 성별
    private String empTel;  // 연락처
    private String email;       // 이메일
    private Position position;      // 직급
    private String addr;        // 주소
    private String dAddr;       // 상세주소
    private LocalDate joinDate;     // 입사일자
    private LocalDate leaceDate;        // 퇴사일자
    private boolean status;      // 퇴사여부
    private String pwd;     // 비밀번호

//    private 부서코드
//    파일
}
