package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByEmployeeEmpId(Long empId);

    /** 특정 기간 안에 있는 스케쥴 가져오기
     * @param start 시작하는 날짜
     * @param end 끝나는 날짜
     * @return
     */
    @Query("SELECT s FROM Schedule s WHERE s.scheStartDate >= :start AND s.scheEndDate <= :end")
    List<Schedule> findByStartAndEnd(@Param("start")LocalDateTime start, @Param("end")LocalDateTime end);
}