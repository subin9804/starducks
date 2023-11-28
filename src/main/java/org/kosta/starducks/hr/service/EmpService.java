package org.kosta.starducks.hr.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.CommonException;
import org.kosta.starducks.hr.entity.EmpEntity;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpService {

    private final EmpRepository repository;

    /**
     * 모든 직원 조회
     * @return
     */
    public List<EmpEntity> getAllEmp() {

        return repository.findAll();
    }

    /**
     * 한명의 직원 조회
     * @param empId
     * @return
     */
    public EmpEntity getEmp(Long empId) {

        return repository.findById(empId).get();
    }

    /**
     * 직원 추가
     * @param emp
     * @return
     */
    public EmpEntity saveEmp(EmpEntity emp) {
        return repository.save(emp);
    }


    /**
     * 직원 퇴사처리
     * @param empId
     */
    public void delEmp (Long empId) {
        EmpEntity emp = repository.findById(empId).orElse(null);

        if(emp != null) {
            if(!emp.isStatus()) {
                emp.setStatus(true);
            } else new CommonException("#{error.already.notexist}");
        } else new CommonException("해당하는 직원이 존재하지 않습니다.");
    }

}
