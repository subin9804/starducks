package org.kosta.starducks.auth.service;

import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * 이메일로 직원 찾고 임시 비밀번호를 이메일로 보내기 서비스
 */
@Service
public class UserService {

  @Autowired
  private EmpRepository empRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

//  비밀번호 찾기 페이지에서 사번, 이메일 일치 여부 확인을 위한 메서드
  public boolean validateEmpIdAndEmail(Long empId, String email) {
    Employee emp = empRepository.findById(empId).orElse(null);
    return emp != null && emp.getEmail().equals(email);
  }

  public Employee findByEmail(String email) {
    return empRepository.findByEmail(email).orElse(null);
  }

  public String getTempPassword(){

    SecureRandom random = new SecureRandom();

    return random.ints(48, 122)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(8) //8자리 임시 비밀번호 생성
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  public void updateEmp(Employee employee) {
    empRepository.save(employee);
  }
}
