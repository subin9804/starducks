package org.kosta.starducks.commons.notify.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String eventCacheId, Object event);

    Map<String, SseEmitter> findAllEmitterStartWithByEmpId(String empId);
    Map<String, Object> findAllEventCacheStartWithByEmpId(String empId);

    void deleteById(String id);
    void deleteAllEmitterStartWithId(String empId);
    void deleteAllEventCacheStartWithId(String empId);
}
