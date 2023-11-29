package org.kosta.starducks.hr.service;

import org.kosta.starducks.hr.entity.EmpEntity;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpService {

    @Autowired
    private EmpRepository repository;

    /**
     * 모든 직원 조회
     * @return
     */
//    public List<EmpEntity> getAllEmp() {
//        List<EmpEntity> all = repository.findAll();
//        if(all.size() != 0) {
//            System.out.println("emps!!" + all.size());
//
//        } else System.out.println("조회가 왜 안될까");
//        return all;
//    }

    /**
     * 한명의 직원 조회
     * @param empId
     * @return
     */
    public EmpEntity getEmp(Long empId) {

        return repository.findById(empId).orElse(null);
    }

    /**
     * 직원 추가
     * @param emp
     * @return
     */
//    public EmpEntity saveEmp(EmpEntity emp) {
//        return repository.save(emp);
//    }


    /**
     * 직원 퇴사처리
     * @param empId
     */
//    public void delEmp (Long empId) {
//        EmpEntity emp = repository.findById(empId).orElse(null);
//
//        if(emp != null) {
//            if(!emp.isStatus()) {
//                emp.setStatus(true);
//            } else new CommonException("#{error.already.notexist}");
//        } else new CommonException("해당하는 직원이 존재하지 않습니다.");
//    }

}
