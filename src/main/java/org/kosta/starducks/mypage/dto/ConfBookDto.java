package org.kosta.starducks.mypage.dto;

import lombok.Getter;
import lombok.Setter;
import org.kosta.starducks.mypage.entity.ConfRoomEN;

@Getter @Setter
public class ConfBookDto {
    private ConfRoomEN room;    // 회의실 이름

    private String confName;    // 회의 이름

    private String text;    // 메모

    private String runningDay;  // 진행 일자

    private String startTime;    // 시작 시간

    private String endTime;  // 종료 시간

    private String color; // 예약 블록 색상
}
