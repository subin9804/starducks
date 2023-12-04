package org.kosta.starducks.auth.service;

import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AccountService implements UserDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final EmpRepository empRepository;

  @Autowired
  public AccountService(@Lazy PasswordEncoder passwordEncoder, EmpRepository empRepository) {
    this.passwordEncoder = passwordEncoder;
    this.empRepository = empRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // username 파라미터를 사원번호(empId)로 변환
    Long empId;
    try {
      empId = Long.parseLong(username);
    } catch (NumberFormatException e) {
      throw new UsernameNotFoundException("Invalid employee ID format");
    }

    Employee employee = empRepository.findById(empId)
        .orElseThrow(() -> new UsernameNotFoundException("Employee not found with empId: " + empId));

    // UserDetails 객체 생성
    return User.builder()
        .username(employee.getEmpId().toString())
        .password(employee.getPwd())
        .authorities(Collections.singletonList(new SimpleGrantedAuthority(employee.getPosition().name())))
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!employee.isStatus()) // status가 true면 퇴사한 것이므로 disabled 처리
        .build();
  }
}
