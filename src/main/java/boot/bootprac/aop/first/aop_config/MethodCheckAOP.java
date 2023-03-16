package boot.bootprac.aop.first.aop_config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/************
 * @info : AOP - Method Check Class
 * @name : MethodCheckAOP
 * @date : 2023/03/16 5:53 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Target 패키지의 메서드가 실행되기 이전, 메서드 이름 로그 출력, Request Param 정보, Return Value 출력
 ************/
@Aspect
@Component
@Slf4j
public class MethodCheckAOP {


    // Target -> aop/controller Package 모든 메서드
    @Pointcut("execution(* boot.bootprac.aop.first.controller..*.*(..))")
    public void cut() {}


    /**
     * cut()에 정의된, 메서드실행 전에 실행됨.
     * @param joinPoint
     */
    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        // 실행되는 Method
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("AOP RUN METHOD NAME = {}", method.getName());

        // Parameter 출력.
        Object[] args = joinPoint.getArgs();
        for (Object obj:args) {
            log.info("AOP Request Param : type={}, Value={}",obj.getClass().getSimpleName(), obj);
        }
    }//before


}
