package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleReposiroty extends JpaRepository<Schedule, Long> {
}
