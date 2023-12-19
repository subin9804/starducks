package org.kosta.starducks.mypage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "notes")
    private String notes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "empId") // 여기서 변경
    private Employee employee;


}