package org.kosta.starducks.hr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter @Setter @Entity
@NoArgsConstructor
public class Department {

    @Id
    private int deptId; // 부서코드
    private String deptName;    // 부서 이름
    private String deptRepTel;  // 부서 대표전화

    @OneToMany(mappedBy="dept")
    private List<Employee> emps;

    public Department(int deptId, String deptName, String deptRepTel) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptRepTel = deptRepTel;
    }
}
