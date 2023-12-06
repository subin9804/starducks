package org.kosta.starducks.mypage.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class ConfBookDto {
    private String confName;    // 회의 이름

    private LocalDate RunningDay;  // 진행 일자

    @DateTimeFormat(pattern="HH:mm:ss")
    private LocalDateTime StartTime;    // 시작 시간

    @DateTimeFormat(pattern="HH:mm:ss")
    private LocalDateTime endTime;  // 종료 시간
}
