package org.kosta.starducks.hr.repository;

import org.kosta.starducks.hr.entity.EmpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<EmpEntity, Long> {

}
