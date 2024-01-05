package org.kosta.starducks.mypage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MYPAGE_SCHEDULE")
@ToString(exclude = "employee")
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

    // ScheduleType 열거형 타입 추가
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @Column(name = "notes")
    private String notes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "empId")
    private Employee employee;
}