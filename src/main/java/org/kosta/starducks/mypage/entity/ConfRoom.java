package org.kosta.starducks.mypage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity @Builder @Data
@AllArgsConstructor @NoArgsConstructor
public class ConfRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long confId;    // 회의 번호

    @Enumerated(EnumType.STRING)
    private ConfRoomEN room;    // 회의실 이름

    private Long bookerId;    // 예약자 아이디

    private String bookerNm;    // 예약자 이름

    private String dept;    // 예약 부서

    private String confName;    // 회의 이름

    private String text;    // 메모

    private LocalDate runningDay;  // 진행 일자

    @DateTimeFormat(pattern="HH:mm:ss")
    private LocalTime startTime;    // 시작 시간

    @DateTimeFormat(pattern="HH:mm:ss")
    private LocalTime endTime;  // 종료 시간

    private String status;  // 회의 상태 - 예약/종료

    private String color; // 예약 블록 색상

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime recordDay;    // 기록 일자
}
