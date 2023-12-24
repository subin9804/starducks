package org.kosta.starducks.hr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

//    @NotBlank(message = "이름은 빈칸일 수 없습니다.")
    @NotBlank
    private String empName;     // 사원이름

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;    // 생년월일

    @NotBlank
    private String gender;  // 성별

    @NotBlank
    private String empTel;  // 연락처

    @NotBlank @Email
    private String email;       // 이메일

    @NotNull
    @Enumerated(EnumType.STRING)
    private Position position;      // 직급

    @NotBlank
    private String postNo;      //우편번호
    @NotBlank
    private String addr;        // 주소
    @NotBlank
    private String dAddr;       // 상세주소

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate joinDate;     // 입사일자

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate leaveDate;        // 퇴사일자

    private boolean status;      // 퇴사여부 (false = 재직중 / true = 퇴사)

    private String pwd;     // 비밀번호


    @ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department dept;    // 부서코드


    @Transient
    private String cmpEmail;    // 회사 이메일

    @PrePersist
    public void generateCmpEmail() {
        this.cmpEmail = this.empId + "@starducks.monster";
    }

}

