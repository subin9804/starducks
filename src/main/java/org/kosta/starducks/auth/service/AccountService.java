package org.kosta.starducks.auth.service;

import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * db에서 사원 데이터가 empId를 통해서 가져와지는지 확인하는 서비스
 * /auth/dto/CustomUserDetails에서 가져오는 걸 정해서 여기로 옴
 */
@Service
public class AccountService implements UserDetailsService {

  private final EmpRepository empRepository;

  @Autowired
  public AccountService(EmpRepository empRepository) {
    this.empRepository = empRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // String으로 저장돼있는 username(empId)을 Long으로 복구
    Long empId;
    try {
      empId = Long.parseLong(username);    } catch (NumberFormatException e) {
      throw new UsernameNotFoundException("Invalid employee ID format");
    }

    Employee employee = empRepository.findById(empId)
        .orElseThrow(() -> new UsernameNotFoundException("Employee not found with empId: " + empId));

    return new CustomUserDetails(employee);
  }
}