package org.kosta.starducks.hr.service;

import jakarta.transaction.Transactional;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    @Autowired
    private EmpRepository repository;

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
        Long id = getLastEmpId();
        emp.setEmpId(id + 1);

        return repository.save(emp);
    }


    /**
     * 직원 퇴사처리
     * @param empId
     */
    @Transactional
//    public void delEmp (Long empId) {
//        EmpEntity emp = repository.findById(empId).orElse(null);
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
