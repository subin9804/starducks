package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreateDocRepository extends JpaRepository<Document, Long>  {
    Optional<Object> findByDocId(Long docId);
}
