package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.Approval;
import org.kosta.starducks.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {


    List<Approval> findByApvEmp_EmpId(Long empId);

    List<Approval> findByDocument_DocId(Long docId);

    Optional<Approval> findByDocument_DocIdAndApvEmp_EmpId(Long docId, Long empId);

    Optional<Approval> findByApvStepAndDocument_DocId(int apvStep, Long docId);
}
