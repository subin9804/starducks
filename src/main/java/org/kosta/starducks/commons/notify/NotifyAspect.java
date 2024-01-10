package org.kosta.starducks.commons.notify;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.commons.notify.service.NotifyInfo;
import org.kosta.starducks.commons.notify.service.NotifyService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j  // 로그 기록
@Aspect // 횡단적인 관심사를 분리하여 코드를 더 모듈화하고 유지보수하기 쉽게 만듦(생명주기, 예외처리, 메서드 호출 등)
@Component  // 빈으로 등록
@EnableAsync    // Spring의 비동기 처리 활성화
@RequiredArgsConstructor
public class NotifyAspect {

    private final NotifyService notifyService;
    private final EmpService empService;

    @Pointcut("@annotation(org.kosta.starducks.commons.notify.NeedNotify)")
    public void annotationPointcut() {
    }

    @Async  // 비동기 메서드
    @AfterReturning(pointcut = "annotationPointcut()", returning = "result") //대상 메소드가 예외를 던지지 않고 정상적으로 반환되었을 때 실행
    public void checkValue(JoinPoint joinPoint, Object result) {
        System.out.println("aspect 호출!!!!!!!!!!!!!!!!!!!!!!!!!!");

        NotifyInfo notifyProxy = (NotifyInfo) result;

        List<Employee> receivers = notifyProxy.getReceivers();
        if(notifyProxy.getNotificationType() == Notify.NotificationType.POST) {
            receivers = empService.getAllEmp();
        }

        for(Employee emp : receivers) {
            notifyService.send(
                    emp,
                    notifyProxy.getNotificationType(),
                    notifyProxy.getMsg(),
                    notifyProxy.getGoUrl()
            );

        log.info("result!! = {}", notifyProxy.getGoUrl());
        }
    }
}
