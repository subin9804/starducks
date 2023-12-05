package org.kosta.starducks.mypage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;

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
    private Employee employee;

}
