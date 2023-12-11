package org.kosta.starducks.document.entity;

public enum ApvStatus {
    PENDING,     // 대기중
//    IN_PROGRESS, // 진행 중: 문서의 진행 중 상태는 대기와 반려/승인이 섞여 있을 때 화면에만 출력 되도록 처리
    REJECTED,   // 반려
    APPROVED; // 승인
}
