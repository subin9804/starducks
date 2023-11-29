package org.kosta.starducks.hr.repository;

import org.kosta.starducks.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<Employee, Long> {

    Employee findTopByOrderByEmpIdDesc();

}
