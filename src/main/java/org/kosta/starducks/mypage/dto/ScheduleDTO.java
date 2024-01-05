package org.kosta.starducks.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kosta.starducks.mypage.entity.ScheduleType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ScheduleDTO {

    private Long scheNo;
    private String scheTitle;
    private LocalDateTime scheStartDate;
    private LocalDateTime scheEndDate;
    private ScheduleType scheduleType;
    private String notes;
    private Long employeeId; // Employee 엔티티 대신 직원의 ID만을 저장

    /** Employee 객체에서 empId를 가져오는 메서드 */
//    public Long getEmpId() {
//        return employee != null ? employee.getEmpId() : null;
//    }
}