package org.kosta.starducks.commons.notify.dto;

public enum NotifyMessage {
    DOCUMENT_NEW_REQUEST("새로운 결재 문서가 도착했습니다."),
    CHAT_NEW_REQUEST("새로운 채팅방이 생성되었습니다."),
    POST_NEW_REQUEST("새로운 공지가 등록되었습니다."),
    SCHEDULE_NEW_REQUEST("새로운 전사일정이 등록되었습니다.");


    private String message;

    NotifyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
