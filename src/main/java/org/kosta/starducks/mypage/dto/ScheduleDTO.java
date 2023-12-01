package org.kosta.starducks.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kosta.starducks.mypage.entity.Schedule;

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
    private Location location;
    private String notes;
    private Long empId;

    /**
     * Schedule 객체를 매핑하는 생성자 직접 작성
     */
    public ScheduleDTO(Schedule schedule) {
        this.scheNo = schedule.getScheNo();
        this.scheTitle = schedule.getScheTitle();
        this.scheStartDate = schedule.getScheStartDate();
        this.scheEndDate = schedule.getScheEndDate();
        this.scheduleType = schedule.getScheduleType();
        this.location = schedule.getLocation();
        this.notes = schedule.getNotes();
        this.empId = schedule.getEmpId();
    }
}
