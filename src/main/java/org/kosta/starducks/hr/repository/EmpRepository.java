package org.kosta.starducks.hr.repository;

import com.querydsl.core.BooleanBuilder;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.entity.QEmployee;
import org.kosta.starducks.roles.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {

    Employee findTopByOrderByEmpIdDesc();
    Employee findByEmpName(String empName);

    List<Employee> findByPosition(Position position);

    // 퇴사여부로 직원 검색
    List<Employee> findByStatus(Boolean status);

    // 이메일로 직원 검색
    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.dept.deptId = :deptId AND e.empName LIKE %:name%")
    List<Employee> findDeptEmployee (@Param("deptId") int deptId,@Param("name") String name);

    List<Employee> findByDept_deptId (int deptId);

    default Page<Employee> getEmployees(EmpSearchCond empSearch) {
        /** 페이징 처리 */
        int page = empSearch.getPage();
        page = page < 1 ? 1: page;
        int limit = empSearch.getLimit();
        limit = limit < 1 ? 15 : limit;

        Pageable pageable = PageRequest.of(page - 1, limit);

        /** 검색 조건 처리 */
        BooleanBuilder builder = new BooleanBuilder();
        QEmployee employee = QEmployee.employee;
        String sopt = empSearch.getSopt();
        String text = empSearch.getText();
        String status = empSearch.getStatus();

        if(sopt != null && !sopt.isBlank() && text != null && !text.isBlank()) {
            if(sopt.equals("dept")) {
                builder.and(employee.dept.deptName.contains(text));
            } else if(sopt.equals("empName")) {
                builder.and(employee.empName.contains(text));
            } else if(sopt.equals("email")) {
                builder.and(employee.email.contains(text));
            } else {
                builder.andAnyOf(employee.dept.deptName.contains(text),
                        employee.empName.contains(text),
                        employee.email.contains(text));
            }
        }

        /** 퇴사여부 옵션 선택 시 */
        if (status != null && !status.isBlank()) {
            if ("stopped".equals(status)) {
                builder.and(employee.status.isTrue());
            } else if ("running".equals(status)) {
                builder.and(employee.status.isFalse());
            }
        }
        else {
            builder.andAnyOf(employee.status.isFalse(),
                    employee.status.isTrue());
        }

        Page<Employee> data = findAll(builder, pageable);
        return data;


    }

    /**
     * 부서 id 순서대로 사원들 정렬해서 가져오기. 채팅 사원 목록에 사용
     */
    @Query("SELECT e FROM Employee e JOIN e.dept d ORDER BY d.deptId ASC")
    List<Employee> findAllEmployeesWithDepartmentOrder();

    @Query("SELECT e FROM Employee e JOIN FETCH e.dept WHERE e.empId = :empId")
    Optional<Employee> findEmployeeWithDepartment(Long empId);
}
