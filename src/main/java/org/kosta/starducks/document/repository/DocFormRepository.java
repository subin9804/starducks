package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.DocForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocFormRepository extends JpaRepository<DocForm, Long> {
    Optional<DocForm> findByFormNameEn(String formNameEn);
    Optional<DocForm> findByFormCode(String formCode);
}
