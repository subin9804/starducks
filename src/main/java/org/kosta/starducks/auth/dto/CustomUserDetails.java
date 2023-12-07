package org.kosta.starducks.auth.dto;

import lombok.Getter;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 사원 상세 정보 제공. 사용자 인증에 필요한 정보들. empId를 통해서 이 데이터들을 갖고 온다
 */
@Getter
public class CustomUserDetails implements UserDetails {

  private final Employee employee;

  public CustomUserDetails(Employee employee) {
    this.employee = employee;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Collection<GrantedAuthority> collection = new ArrayList<>();

    collection.add((GrantedAuthority) () -> String.valueOf(employee.getPosition()));

    return collection;
  }

  @Override
  public String getPassword() {
    return employee.getPwd();
  }

  @Override
  public String getUsername() {
    return employee.getEmpName();
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
