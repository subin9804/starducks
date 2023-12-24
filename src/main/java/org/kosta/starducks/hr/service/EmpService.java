package org.kosta.starducks.hr.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Department;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpService {

    private final EmpRepository repository;
    private final EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 모든 직원 조회
     */
    @Transactional
    public List<Employee> getAllEmp() {
        List<Employee> emps = repository.findAll();
        return emps;
    }

    /**
     * 직원 검색
     * @param cond 검색조건
     * @return
     */
    @Transactional
    public Page<Employee> toSearchEmp (EmpSearchCond cond) {
        Page<Employee> emps = repository.getEmployees(cond);
        emps.stream().forEach(em::detach);  // 영속성 분리
        return emps;
    }

    /**
     * 직원 검색
     * @param deptId 부서번호와 사원이름으로 검색하는 쿼리
     * @return
     */
    @Transactional
    public List<Employee> getEmp(int deptId, String name) {

        return repository.findDeptEmployee(deptId, name);
    }
    /**
     * 직원 검색
     * @param deptId 부서번호로 검색하는 쿼리
     * @return
     */
    @Transactional
    public List<Employee> getEmp(int deptId) {

        return repository.findByDept_deptId(deptId);
    }

    /**
     * 한명의 직원 조회
     * @param empId 아이디로 조회
     * @return
     */
    @Transactional
    public Employee getEmp(Long empId) {

        return repository.findById(empId).orElse(null);
    }



    /**
     * 직원 추가
     * @param emp
     * @return
     */
    @Transactional
    public Employee saveEmp(Employee emp) {

        System.out.println("NEW!!:" + emp);
        if(emp.getEmpId() == null) {
            // 현재 존재하는 가장 높은 번호의 사원보다 1 높은 숫자를 부여
            Long id = getLastEmpId();
            emp.setEmpId(id + 1);

//            비밀번호 암호화해서 사원 등록~~~
            String newPassword = "PASS_" + emp.getEmpId();

//            자동으로 등록되는 임시 사원들은 그냥 그대로 쓰면 됨
            if(emp.getPwd() != null) {
                newPassword = emp.getPwd();
            }
            System.out.println("비밀번ㄴ호!!!!!!!!!!" + newPassword);

            String encodedPassword = passwordEncoder.encode(newPassword);
            emp.setPwd(encodedPassword);

            System.out.println("등록한다");

            return repository.save(emp);


        } else {

            Employee employee = repository.findById(emp.getEmpId()).orElse(null);
            employee.setEmpName(emp.getEmpName());
            employee.setBirth(emp.getBirth());
            employee.setGender(emp.getGender());
            employee.setEmpTel(emp.getEmpTel());
            employee.setEmail(emp.getEmail());
            employee.setPosition(emp.getPosition());
            employee.setPostNo(emp.getPostNo());
            employee.setAddr(emp.getAddr());
            employee.setDAddr(emp.getDAddr());
            employee.setDept(emp.getDept());

            if(emp.isStatus() != employee.isStatus()) {
                employee.setStatus(emp.isStatus());
                employee.setLeaveDate(LocalDate.now());
            }

            System.out.println("수정한다");
            return repository.save(employee);
        }
    }

    /**
     * 직원 퇴사처리
     * @param empId
     */
//    @Transactional
//    public void delEmp (Long empId) {
//        Employee emp = repository.findById(empId).orElse(null);
//
//        if(emp != null) {
//            if(!emp.isStatus()) {
//                emp.setStatus(true);
//            } else new CommonException("#{error.already.notexist}");
//        } else new CommonException("해당하는 직원이 존재하지 않습니다.");
//    }


    /**
     * 가장 높은 사번을 기준으로 그 다음 사람은 +1하여 사번이 부여된다.
     * 첫 사원인 경우 그냥 1이 부여된다.
     * @return
     */
    public Long getLastEmpId() {
        Employee emp = repository.findTopByOrderByEmpIdDesc();
        if (emp != null) {
            System.out.println(emp);
            return emp.getEmpId();
        }
        return 1L;
    }

    /**
     * 직원의 비밀번호 변경
     * @param empId 직원 ID
     * @param oldPassword 현재 비밀번호
     * @param newPassword 새로운 비밀번호
     * @return 변경 성공 여부
     */
    @Transactional
    public boolean changePassword(Long empId, String oldPassword, String newPassword) {
        Employee employee = repository.findById(empId).orElse(null);

        // 직원이 존재하고, 현재 비밀번호가 일치하는 경우에만 비밀번호 변경
        if (employee != null && passwordEncoder.matches(oldPassword, employee.getPwd())) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            employee.setPwd(encodedNewPassword);
            repository.save(employee);
            return true;
        }

        return false;
    }

    /**
     * 부서별로 직원 조회 하기
     */
    @Transactional
    public Map<Department, List<Employee>> getAllEmpGroupedByDept() {
        List<Employee> employees = repository.findAllEmployeesWithDepartmentOrder();
        return employees.stream()
            .collect(Collectors.groupingBy(Employee::getDept,
                LinkedHashMap::new, // 정렬 순서 유지
                Collectors.toList()));
    }

    /**
     * 로그인한 사원, 퇴사한 사원 제외하고 모든 사원을 부서별로 가져오기. 실시간 채팅 사원 목록에서 사용 중
     * @param loggedInUserId
     * @return
     */
    @Transactional
    public Map<Department, List<Employee>> getAllEmpExcludingLoggedInUser(Long loggedInUserId) {
        List<Employee> employees = repository.findAll();
        return employees.stream()
            .filter(emp -> !emp.getEmpId().equals(loggedInUserId)) // 로그인한 사용자 제외
            .filter(emp -> !emp.isStatus()) // 퇴사한 사원 제외 (status가 true인 사람 제외)
            .collect(Collectors.groupingBy(Employee::getDept, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * empId를 넣으면 어떤 부서 사람인지도 바로 같이 가져와진다. 부서 LAZY 대응용
     * 로그인한 사원이 어떤 부서인지 확인하려고 제작
     * @param empId
     * @return
     */
    @Transactional
    public Employee getEmployeeWithDepartment(Long empId) {
        return repository.findEmployeeWithDepartment(empId).orElse(null);
    }

}
