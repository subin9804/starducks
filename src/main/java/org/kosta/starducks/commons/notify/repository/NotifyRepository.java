package org.kosta.starducks.commons.notify.repository;

import org.kosta.starducks.commons.notify.entity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotifyRepository extends JpaRepository<Notify, Long> {

    @Query(value = "SELECT * FROM notify WHERE emp_id = :empId ORDER BY created_at DESC LIMIT 4", nativeQuery = true)
    List<Notify> findLatest4NotifyNativeQuery(@Param("empId") Long empId);
}
