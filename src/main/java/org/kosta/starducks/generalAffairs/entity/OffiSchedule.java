package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;

/**
 * 공식 전사 일정
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "OFFICIAL_SCHEDULE")
public class OffiSchedule {
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

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "empId") // 여기서 변경
    private Employee employee;
}
