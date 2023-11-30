package org.kosta.starducks.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalendarDTO {

//  개인일정(PERSONAL_SCHEDULE)
//  일정번호        scheNo                  (INT) Long
//  일정생성일시     sche_create_time        DATETIME
//  일정시작날짜     sche_start_date         DATE
//  일정종료날짜     sche_end_date           DATE
//  일정명          sche_title              VARCHAR
//  일정상세        sche_detail               VARCHAR
//  사원번호(fk)    emp_id                  (BIGINT)LONG
//  일정유형        sche_cate               ENUM('PERSONAL', 'TEAM')

//    private Long scheNo;
//    private LocalDateTime scheCreateTime;
//    private LocalDate scheStartDate;
//    private LocalDate scheEndDate;
//    private String scheTitle;
//    private String scheDetail;
//    private Long empId;
//    private ScheduleCategory scheduleCategory;


}
