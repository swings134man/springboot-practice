package boot.bootprac.jwt_server.aop;

import boot.bootprac.jwt_server.jwt_common.JwtProvider_server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

import java.util.Map;

/************
 * @info : Jwt 요청 검사 로직 - AOP
 * @name : JwtAopConfig
 * @date : 2023/03/23 6:35 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Request 가 발생 했을때 Token 의 유효성을 검증한다.
 *
 * -> 토큰의 유효성검사와 더불어 AT Vali, RT Vali Check 후
 * -> RT 를 통한 AT 재발급의 역할또한 이루어진다.
 ************/
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAopConfig {

    private final JwtProvider_server jwtProviderServer;

    // JwtServer/Controller Package의 Method명이 aop로 시작하는 파라미터가 0개이상인 모든 메서드
    @Pointcut("execution(* boot.bootprac.jwt_server.controller..*.aop*(..))")
    public void cut() {}

    // checkToken - 메서드 실행전에 token 값 확인.
    @Around("cut()")
    public Map<String, String> checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        String newAT = "";

        // 1. Parameter
        Object[] args = joinPoint.getArgs();
        String at = (String) args[0];
        String rt = (String) args[1];

        log.info("at {}", at);
        log.info("rt {}", rt);

        String[] atRes = at.split(" ");
        String[] rtRes = rt.split(" ");

        // 2. Token 형식 Check
        if(!atRes[0].equals("Bearer") || !rtRes[0].equals("Bearer")) {
            throw new RequestRejectedException("토큰 요청형식이 잘못됨 : Bearer");
        }else if(at.isEmpty() || rt.isEmpty()) {
            throw new RequestRejectedException("요청 토큰값이 존재하지 않음.");
        }

        // 3. AT Check
        boolean atStatus = jwtProviderServer.validateToken(at.split(" ")[1]);

        // 4. AT 만료시.
        if(!atStatus) {
            newAT = jwtProviderServer.refreshTokenValidation(rt.split(" ")[1]);

            // RT 만료시
            if(newAT.equals("null")) {
                throw new IllegalArgumentException("Refresh Token 만료 재로그인 필요");
            }
        }

        // 5. Method 실행.
        Map<String, String> proceed = (Map)joinPoint.proceed();
        proceed.put("access_token", newAT);

        return proceed;
    }//before


}
