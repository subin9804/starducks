package org.kosta.starducks.hr.repository;

import com.querydsl.core.BooleanBuilder;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.entity.QEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EmpRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {

    Employee findTopByOrderByEmpIdDesc();

//    List<Employee> dynamicSearch(EmpSearchCond empSearch);
//    Page<Employee> pagination(EmpSearchCond empSearch, Pageable pageable);

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

//        builder.and(andBuilder);

        Page<Employee> data = findAll(builder, pageable);
        return data;
    }
}
