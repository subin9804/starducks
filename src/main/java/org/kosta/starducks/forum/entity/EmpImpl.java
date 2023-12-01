package org.kosta.starducks.forum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * 사원 테이블 임시적으로 사용하는 것. 제대로 된 사원 엔티티 생기면 나중에 지울 예정ㄴㅇㄹ
 */
@Entity
public class EmpImpl {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long empId; //사원 고유 ID

  public Long getEmpId() {
    return empId;
  }

  public void setEmpId(Long empId) {
    this.empId = empId;
  }
}
