package boot.bootprac.aop.first.aop_config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/************
 * @info : Timer AOP Class
 * @name : TimerAop
 * @date : 2023/03/16 5:11 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 각 메서드의 실행시간을 측정. 어떤 시점에 해당 기능을 적용할지 명시 되어있는 클래스.
 *
 * - execution : Advice를 적용할 Method를 명시할 때 사용.
 *      -> "*" : 모든 값을 표현.
 *      -> ".." " 0개 이상의 값을 의미함.
 *
 ************/
@Aspect
@Component
@Slf4j
public class TimerAop {

    // AnnoTation 경로 -> 정의한 어노테이션.
    @Pointcut("@annotation(boot.bootprac.aop.first.annotation.Timer)")
    public void enableTimer() {}

    // Target Package
    // boot.bootprac 패키지 및 하위 패키지에 속해 있고, 파라미터가 0개 이상인 모든 Method
    // 1개 이상 -> controller.*.*(*)
    @Pointcut("execution(* boot.bootprac.aop.first.controller..*.*(..))")
    public void cut() {}


    // Around (before/after)를 적용해야 메서드의 실행시간을 측정가능 -> 종료시간 - 시작시간 이기때문.
    @Around("cut() && enableTimer()")
    public void aroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        // Method Start
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Object Return 시 (리턴 데이터가 존재하면), Object로 Return
        // void 의 경우 return 없음.
        Object proceed = joinPoint.proceed(); //proceed() -> 메서드 실행.

        // Return after
        stopWatch.stop();

        log.info("AOP Total Run Time = {}", stopWatch.getTotalTimeSeconds());
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterRun(JoinPoint joinPoint, Object returnObj) {
        log.info("AOP Timer Return Value = {}", returnObj);
    }

}
