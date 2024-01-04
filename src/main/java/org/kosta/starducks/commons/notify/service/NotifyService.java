package org.kosta.starducks.commons.notify.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.commons.notify.NeedNotify;
import org.kosta.starducks.commons.notify.dto.NotifyDto;
import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.commons.notify.repository.EmitterRepository;
import org.kosta.starducks.commons.notify.repository.NotifyRepository;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotifyService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final NotifyRepository notifyRepository;

    @NeedNotify
    public void methodWithAnt() {
        // 해당 메서드가 실행될 때 NotifyAspect의 checkValue 메서드가 실행되어야 함
        System.out.println("Method with @NeedNotify annotation");
    }

    public void methodWithoutAnt() {
        // 해당 메서드가 실행될 때 NotifyAspect의 checkValue 메서드는 실행되지 않아야 함
        System.out.println("Method without @NeedNotify annotation");
    }



    public SseEmitter subscribe(String empId, String lastEventId) {
        String emitterId = makeTimeIncludeId(empId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        //이벤트가 완료되거나 연결이 종료되면 Map에서 제거
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503에러를 방지하기 위한 더미이벤트 전송
        String eventId = makeTimeIncludeId(empId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [empId=" + empId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유시ㅣㄹ을 방지
        if(hasLostData(lastEventId)) {
            sendLostData(lastEventId, empId, emitterId, emitter);
        }
        return emitter;
    }



    private void sendLostData(String lastEventId, String empId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByEmpId(String.valueOf(empId));
        // lastEventId와 비교하여 보다 큰 키를 가진 엔트리들(전송 안된 이벤트)만을 선택하여 이벤트 전송
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));

    }

    /**
     * Notify객체를 생성하여 지정된 수신자에게 알림 전송
     * @param receiver
     * @param notificationType
     * @param content
     * @param url
     */
    public void send(Employee receiver, Notify.NotificationType notificationType, String content, String url) {
        // DB에 알림내용 저장
        Notify notification = notifyRepository.save(createNotification(receiver, notificationType, content, url));

        String receiverId = receiver.getEmpId().toString();
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByEmpId(receiverId);

        // 클라이언트에 이벤트 전송
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotifyDto.Response.createResponse(notification));
                }
            );
    }

    /**
     * SseEmitter를 통해 이벤트를 전송하는 메서드
     * @param emitter   이벤트 전송 대상
     * @param eventId   이벤트 전송 대상
     * @param emitterId 식별하기위한 고유ID
     * @param data  전송할 데이터 객체
     */
    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
//        System.out.println("subscribe !!!!!!!aasdfasdfasdfad됨:" + emitterId);
//        System.out.println("dataaaaaaaaaaaaaaaa" + data.toString() + " 이거는 " + data);
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("message")
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    /**
     * 마지막 이벤트Id를 기반으로 구독자가 받지 목한 데이터가 있는지 확인
     * lastEventId가 비어있지 않다 -> 손실된 이벤트가 있다(true)
     * @param lastEventId
     * @return
     */
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }




    /**
     * 사원아이디 + 현재 시간으로 각 이벤트의 아이디 지정
     * @param empId
     * @return
     */
    private String makeTimeIncludeId(String empId) {
        return empId + "_" + System.currentTimeMillis();
    }

    private Notify createNotification(Employee receiver, Notify.NotificationType notificationType, String content, String url) {
        return Notify.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

}
