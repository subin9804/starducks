package org.kosta.starducks.mypage.dto;

import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceDto {
    private Long workId;
    private LocalDate workDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Employee emp;
}
