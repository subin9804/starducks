package org.kosta.starducks.commons.notify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 새로운 Custom Annotation 정의.
// 이 Annotation은 PointCut이 되어 Annotation이 붙은 메서드가 동작할 때 Aspect에 정의한 작업이 수행됨.


@Retention(RetentionPolicy.RUNTIME) // RunTime까지 기능을 유지
@Target({ElementType.METHOD})   // 메서드 단위에 붙이는 애너테이션
public @interface NeedNotify {
}
