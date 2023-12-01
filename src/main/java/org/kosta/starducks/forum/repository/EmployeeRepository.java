package org.kosta.starducks.forum.repository;

import org.kosta.starducks.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
