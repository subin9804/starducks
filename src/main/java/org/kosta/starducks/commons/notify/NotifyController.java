package org.kosta.starducks.commons.notify;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notify")
public class NotifyController {
    private final NotifyService notifyService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")String lastEventId) {
        System.out.println("---------SseEmitter subscribe 일단 호출되긴 함---------------------");
        SseEmitter emitter = notifyService.subscribe(userDetails.getUsername(), lastEventId);

        return emitter;
    }

}
