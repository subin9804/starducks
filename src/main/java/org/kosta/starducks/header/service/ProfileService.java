package org.kosta.starducks.header.service;

import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 개인정보 수정 페이지에서 정보 수정할 때 사용하는 서비스
 */
@Service
public class ProfileService {

  @Autowired
  private EmpRepository empRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public boolean checkCurrentPassword(String currentPassword, Employee employee) {

      return passwordEncoder.matches(currentPassword, employee.getPwd());
  }

  public boolean isValidPassword(String password) {
    String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    return password != null && password.matches(passwordRegex);
  }

  public  Employee getEmployeeById(Long empId) {
    return empRepository.findById(empId).orElse(null);
  }


  @Transactional
  public boolean updatePassword(Long empId, String newPassword) {
    //새로운 비밀번호가 유효성 검사 적합하면 다음 단계
    if (!isValidPassword(newPassword)) {
      return false;
    }
    Employee employee = empRepository.findById(empId).orElse(null);
    if (employee != null) {
      employee.setPwd(passwordEncoder.encode(newPassword));
      empRepository.save(employee);
      return true;
    }
    return false;
  }

  @Transactional
  public void updateProfile(Employee updatedEmp) {
    // 기존 직원 정보 찾기
    Employee existingEmp = empRepository.findById(updatedEmp.getEmpId()).orElse(null);

    // 기존 직원이 존재하는 경우 정보 업데이트
    if (existingEmp != null) {
      // 수정 가능한 필드 업데이트
      existingEmp.setEmpTel(updatedEmp.getEmpTel()); // 연락처 업데이트
      existingEmp.setEmail(updatedEmp.getEmail());   // 이메일 업데이트

      // 업데이트된 정보 저장
      empRepository.save(existingEmp);
    }
  }
}

