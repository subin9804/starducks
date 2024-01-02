package org.kosta.starducks.commons.notify.dto;

import lombok.*;
import org.kosta.starducks.commons.notify.entity.Notify;

// NotifyService의 send()에서 SSE를 클라이언트에게 전송할 때 이벤트의 데이터로 전송할 DTO
public class NotifyDto {

    @Getter @Setter @Builder @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class Response {
        String id;
        String name;
        String content;
        String type;
        String url;
        String createdAt;

        public static Response createResponse(Notify notify) {
            return Response.builder()
                    .content(notify.getContent())
                    .id(notify.getId().toString())
                    .name(notify.getReceiver().getEmpName())
                    .type(String.valueOf(notify.getNotificationType()))
                    .url(notify.getUrl())
                    .createdAt(notify.getCreatedAt().toString())
                    .build();
        }
    }
}
