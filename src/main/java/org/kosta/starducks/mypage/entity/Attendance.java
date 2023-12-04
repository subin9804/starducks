package org.kosta.starducks.mypage.entity;


import jakarta.persistence.*;
import lombok.*;
import org.kosta.starducks.hr.entity.EmpEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ATTENDANCE")
public class Attendance {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long workId;

        private LocalDate workDate;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "emp_id", nullable = false)
        private EmpEntity emp;

        public Long getEmpId() {
                return emp != null ? emp.getEmpId() : null;
        }

        public void setEmpId(Long empId) {
                if (emp == null) {
                        emp = new EmpEntity();
                }
                emp.setEmpId(empId);
        }
}