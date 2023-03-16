package boot.bootprac.aop.first.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/************
 * @info : Anootation 정의 (Annotation Processor) - Timer
 * @name : Timer
 * @date : 2023/03/16 5:07 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Method 실행 시간 측정 Annotation
 *
 * - RunTime 시점 적용.
 *      -> 컴파일 -> 클래스 로딩 -> main() 실행 이후 기능 제공.
 *      -> Proxy를 통해 부가 기능 제공 하는 방식.
 *      -> Method의 실행 지점으로 제한됨.
 ************/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
    // Annotation 정의.
}
