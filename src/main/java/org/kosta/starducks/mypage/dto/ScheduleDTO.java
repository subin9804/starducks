package org.kosta.starducks.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.mypage.entity.Schedule;
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
    private Employee employee;

    /**
     * DTO를 Entity로 변환하는 메서드
     * @return
     */
    public Schedule toEntity() {
        return new Schedule(scheNo, scheTitle, scheStartDate, scheEndDate,
                scheduleType, notes, employee);
    }
}
