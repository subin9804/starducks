package org.kosta.starducks.hr.repository;

import org.kosta.starducks.hr.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Department, Integer> {
    Boolean existsByDeptId(int deptId);
}
