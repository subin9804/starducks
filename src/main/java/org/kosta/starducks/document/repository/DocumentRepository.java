package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long>  {
    Optional<Object> findByDocId(Long docId);

    Optional<Object> findByDocWriter_EmpId(Long empId);

    Optional<Object> findByDocForm_FormNameEn(String formNameEn);
}
