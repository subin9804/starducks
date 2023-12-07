package org.kosta.starducks.mypage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity @Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ConfRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long confId;    // 회의 번호

    @Enumerated(EnumType.STRING)
    private ConfRoomEN room;    // 회의실 이름

    @ManyToOne
    private Employee booker;    // 예약자

    private String confName;    // 회의 이름

    private LocalDate runningDay;  // 진행 일자

    @DateTimeFormat(pattern="HH:mm:ss")
    private LocalTime StartTime;    // 시작 시간

    @DateTimeFormat(pattern="HH:mm:ss")
    private LocalTime endTime;  // 종료 시간

    private String status;  // 회의 상태

    @CreatedDate
    private LocalDateTime recordDay;    // 기록 일자
}
