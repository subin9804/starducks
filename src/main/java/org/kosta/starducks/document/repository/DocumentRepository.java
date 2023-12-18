package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long>  {
    Optional<Object> findByDocId(Long docId);

    List<Document> findByDocWriter_EmpId(Long empId);

    Optional<Object> findByDocForm_FormNameEn(String formNameEn);

//    Page<Document> findByDocTitleContainingOrDocContentContaining(String title, String content, Pageable pageable);
    List<Document> findByDocTitleContainingOrDocContentContaining(String title, String content);
}
