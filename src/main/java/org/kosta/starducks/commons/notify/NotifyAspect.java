package org.kosta.starducks.commons.notify;

import groovy.util.logging.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j  // 로그 기록
@Aspect // 횡단적인 관심사를 분리하여 코드를 더 모듈화하고 유지보수하기 쉽게 만듦(생명주기, 예외처리, 메서드 호출 등)
@Component  // 빈으로 등록
@EnableAsync    // Spring의 비동기 처리 활성화
public class NotifyAspect {
}
