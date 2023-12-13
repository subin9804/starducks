package org.kosta.starducks.hr.repository;

import org.kosta.starducks.hr.entity.EmpFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpFileRepository extends JpaRepository<EmpFile, Long> {

    EmpFile findByEmpIdAndType(Long id, String type);
}
