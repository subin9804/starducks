package org.kosta.starducks.document.repository;

import org.kosta.starducks.document.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
}
