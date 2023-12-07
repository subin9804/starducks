package org.kosta.starducks.hr.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.dto.EmpSearchCond;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpService {

    private final EmpRepository repository;
    private final EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 모든 직원 조회
     * @return
     */
    @Transactional
    public List<Employee> getAllEmp() {
        List<Employee> emps = repository.findAll();
        return emps;
    }

    /**
     * 직원 검색
     */
    @Transactional
    public Page<Employee> toSearchEmp (EmpSearchCond cond) {
        Page<Employee> emps = repository.getEmployees(cond);
        emps.stream().forEach(em::detach);  // 영속성 분리
        return emps;
    }

    /**
     * 한명의 직원 조회
     * @param empId
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
            String encodedPassword = passwordEncoder.encode(emp.getPwd());
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
            employee.setStatus(emp.isStatus());
            employee.setDept(emp.getDept());



            System.out.println("수정한다");
            return repository.save(emp);

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

}
