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
    private Long empId;

}