package org.kosta.starducks.hr.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.roles.Position;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class EmpDto {
    private Long empId; // 사원번호

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

    private boolean status;      // 퇴사여부 (false = 재직중 / true = 퇴사)

    @NotNull
    private Department dept;    // 부서코드
}
