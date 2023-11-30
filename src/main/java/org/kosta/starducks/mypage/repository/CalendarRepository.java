package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
