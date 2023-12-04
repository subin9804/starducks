package org.kosta.starducks.mypage.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;

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
        private Employee emp;

        public Long getEmpId() {
                return emp != null ? emp.getEmpId() : null;
        }

        public void setEmpId(Long empId) {
                if (emp == null) {
                        emp = new Employee();
                }
                emp.setEmpId(empId);
        }
}