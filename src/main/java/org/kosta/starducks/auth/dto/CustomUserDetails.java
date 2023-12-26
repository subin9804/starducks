package org.kosta.starducks.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.roles.Position;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 사원 상세 정보 제공. 사용자 인증에 필요한 정보들. empId를 통해서 이 데이터들을 갖고 온다
 */
@Setter
@Getter
public class CustomUserDetails implements UserDetails {

  private Employee employee;

  public CustomUserDetails(Employee employee) {
    this.employee = employee;
  }

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
  Collection<GrantedAuthority> authorities = new ArrayList<>();

  // 부서별 권한 추가
  String deptName = employee.getDept().getDeptName();
  if (deptName.equals("재무부")) {
    authorities.add(new SimpleGrantedAuthority("FINA"));
  } else if (deptName.equals("인사부")) {
    authorities.add(new SimpleGrantedAuthority("HR"));
  } else if (deptName.equals("물류유통부")) {
    authorities.add(new SimpleGrantedAuthority("LOGISTIC"));
  } else if (deptName.equals("총무부")) {
    authorities.add(new SimpleGrantedAuthority("GENERAL"));
  }

  // Position.ROLE_BOSS를 가진 경우 마스터 권한 추가
  if (employee.getPosition() == Position.ROLE_BOSS) {
    authorities.add(new SimpleGrantedAuthority("ROLE_BOSS"));
  }


  return authorities;
}

  @Override
  public String getPassword() {
    return employee.getPwd();
  }

  @Override
  public String getUsername() {
    return String.valueOf(employee.getEmpId());
  }



  @Override //계정이 만료되었는가? 우리는 안 필요할듯
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override //계정이 잠겼는가? 우리는 안 필요할듯
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override //비밀번호?가 만료되었는가? 우리는 안 필요할듯
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override // status가 false면(퇴사하지 않았으면) 계정을 활성화된 것으로 간주
  public boolean isEnabled() {
    return !employee.isStatus();
  }
}