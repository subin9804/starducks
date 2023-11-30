package org.kosta.starducks.mypage.dto;

import lombok.Data;
import org.kosta.starducks.hr.entity.EmpEntity;

import java.time.LocalDateTime;

@Data
public class ScheduleDTO {

    //  개인일정(PERSONAL_SCHEDULE)
//  일정번호        scheNo             (INT) Long
//  일정명          scheTitle          VARCHAR
//  시작일시        scheStartDate       DATE
//  종료일시        scheEndDate         DATE
//  일정종류        calendarType        VARCHAR
//  장소           location            VARCHAR
//  참고 사항       notes               VARCHAR
    private Long scheNo;
    private String scheTitle;
    private LocalDateTime scheStartDate;
    private LocalDateTime scheEndDate;
    private ScheduleType scheduleType;
    private Location location;
    private String notes;
    private EmpEntity emp;

}
