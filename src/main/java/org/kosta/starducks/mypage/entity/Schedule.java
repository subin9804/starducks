package org.kosta.starducks.mypage.entity;

//  개인일정(PERSONAL_SCHEDULE)
//  일정번호        scheNo             (INT) Long
//  일정명          scheTitle          VARCHAR
//  시작일시        scheStartDate       DATE
//  종료일시        scheEndDate         DATE
//  일정종류        calendarType        VARCHAR
//  장소           location            VARCHAR
//  참고 사항       notes               VARCHAR

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.EmpEntity;
import org.kosta.starducks.mypage.dto.ScheduleType;
import org.kosta.starducks.mypage.dto.Location;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PERSONAL_SCHEDULE")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sche_no")
    private Long scheNo;

    @Column(name = "sche_title")
    private String scheTitle;

    @Column(name = "sche_start_date")
    private LocalDateTime scheStartDate;

    @Column(name = "sche_end_date")
    private LocalDateTime scheEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "calendar_type")
    private ScheduleType scheduleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Location location;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private EmpEntity emp;

}
