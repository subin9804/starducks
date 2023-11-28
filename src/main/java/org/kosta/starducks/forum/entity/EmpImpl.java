package org.kosta.starducks.forum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
