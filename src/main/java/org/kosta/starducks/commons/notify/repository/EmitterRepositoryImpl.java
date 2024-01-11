package org.kosta.starducks.commons.notify.repository;

import org.kosta.starducks.commons.notify.repository.EmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    // ConcurrentHashMap: 여러 스레드에서 동시에 안전하게 데이터에 접근할 수 있는 맵 구현체\
    // 맵 형태를 사용함으로서 데이터 저장, 고유성 보장, 성능 등의 이점을 얻으며 효율적으로 객체를 관리
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByEmpId(String empId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(empId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByEmpId(String empId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(empId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String id) {
        emitters.remove(id);
    }

    @Override
    public void deleteAllEmitterStartWithId(String empId) {
        emitters.forEach(
                (key, emitter) -> {
                    if(key.startsWith(empId)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithId(String empId) {
        eventCache.forEach(
                (key, emitter) -> {
                    if(key.startsWith(empId)) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
