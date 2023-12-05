package org.kosta.starducks.hr.dto;

import lombok.Data;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.roles.Position;

import java.time.LocalDate;

@Data
public class EmpDto {

    private String empName;     // 사원이름

    private LocalDate birth;    // 생년월일

    private String gender;  // 성별

    private String empTel;  // 연락처

    private String email;       // 이메일

    private Position position;      // 직급

    private String postNo;      //우편번호
    private String addr;        // 주소
    private String dAddr;       // 상세주소


    private boolean status;      // 퇴사여부

    private Department dept;    // 부서코드
}
