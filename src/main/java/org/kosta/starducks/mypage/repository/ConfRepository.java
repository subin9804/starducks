package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.ConfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConfRepository extends JpaRepository<ConfRoom, Long> {

    @Query("SELECT c FROM ConfRoom c WHERE c.runningDay = :day")
    List<ConfRoom> findByRunningDay(@Param("day") LocalDate day);

}
