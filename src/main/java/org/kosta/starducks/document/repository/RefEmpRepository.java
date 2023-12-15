package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.RefEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefEmpRepository extends JpaRepository<RefEmployee, Long> {
}
