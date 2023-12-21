package org.kosta.starducks.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@Getter @Setter @Entity @Builder
@NoArgsConstructor @AllArgsConstructor
public class Department {

    @Id
    private int deptId; // 부서코드
    private String deptName;    // 부서 이름 (한글)
    private String deptNameEng; // 부서 이름 (영어)
    private String deptRepTel;  // 부서 대표전화

    @OneToMany(mappedBy="dept")
    private List<Employee> emps;

    public Department(int deptId, String deptName, String deptNameEng, String deptRepTel) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptNameEng = deptNameEng;
        this.deptRepTel = deptRepTel;
    }

    /** 해당 부서에 재직중인 인원 */
    public int getRunningEmp() {
        int count = 0;
        for(Employee emp : emps) {
            if(!emp.isStatus()) {
                count++;
            }
        }
        return count;
    }

    public String getRoleForDeptName() {
        switch(this.deptName) {
            case "재무부":
                return "ROLE_FINA";
            case "인사부":
                return "ROLE_HR";
            case "물류유통부":
                return "ROLE_LOGISTIC";
            case "총무부":
                return "ROLE_GENERAL";
            case "사장실":
                return "ROLE_EXEC";
            default:
                return "ROLE_UNKNOWN";
        }
    }
}

