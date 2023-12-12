package org.kosta.starducks.document.entity;

public enum DocStatus {
    TEMP_STORED, //임시저장
    PENDING_DOC, //문서 결재 대기 중
    IN_PROGRESS, //문서 결재 진행 중
    APPROVED_DOC, //문서 최종 승인
    REJECTED_DOC; //문서 반려
}
