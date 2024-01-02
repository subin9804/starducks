package org.kosta.starducks.commons.notify.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.commons.notify.service.NotifyService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class NotifyController {
    private final NotifyService notifyService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")String lastEventId) throws IOException {
//        System.out.println("---------SseEmitter subscribe 일단 호출되긴 함---------------------");
        SseEmitter emitter = notifyService.subscribe(userDetails.getUsername(), lastEventId);

        return emitter;
    }

//    @RequestMapping("/sse")
//    public SseEmitter sse() {
//        SseEmitter emitter = new SseEmitter();
//
//        // 서버에서 주기적으로 이벤트를 전송
//        new Thread(() -> {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    // 각 이벤트의 이름을 "message"로 설정
//                    emitter.send(SseEmitter.event().name("message").data("Event " + i));
//                    Thread.sleep(1000); // 1초 간격으로 이벤트 전송
//                }
//            } catch (Exception e) {
//                // 예외 처리
//            } finally {
//                emitter.complete(); // 연결 종료
//            }
//        }).start();
//
//        return emitter;
//    }

}
