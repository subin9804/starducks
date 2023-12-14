package org.kosta.starducks.hr.entity;

import jakarta.persistence.*;
import lombok.*;
import org.kosta.starducks.roles.Position;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity @Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    private Long empId; // 사원번호

    private String empName;     // 사원이름

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;    // 생년월일

    private String gender;  // 성별

    private String empTel;  // 연락처

    private String email;       // 이메일

    @Enumerated(EnumType.STRING)
    private Position position;      // 직급

    private String postNo;      //우편번호
    private String addr;        // 주소
    private String dAddr;       // 상세주소

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate joinDate;     // 입사일자

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate leaveDate;        // 퇴사일자

    private boolean status;      // 퇴사여부
    private String pwd;     // 비밀번호

    @ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department dept;    // 부서코드


    @Transient
    private String cmpEmail;    // 회사 이메일

    @PrePersist
    public void generateCmpEmail() {
        this.cmpEmail = this.empId + "@starbucks.monster";
    }

}

