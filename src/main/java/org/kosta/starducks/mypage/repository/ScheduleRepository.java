package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByEmployeeEmpId(Long empId);
}