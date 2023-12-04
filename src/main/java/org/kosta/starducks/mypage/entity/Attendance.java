package org.kosta.starducks.mypage.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

        // 추후 연차 테이블과 연결 - workDate가 휴가 시작일 ~ 종료일 사이면 True, 아니면 False (디폴트값)
        @ColumnDefault(value = "false")
        private boolean isVacation;

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