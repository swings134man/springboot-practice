package boot.bootprac.jwt_server.controller;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import boot.bootprac.jwt_server.domain.JwtResponseServerDTO;
import boot.bootprac.jwt_server.service.JwtServerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/************
 * @info : Jwt Server Side API TEST
 * @name : JwtServerControllerTest
 * @date : 2023/03/20 6:38 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 인스턴스 변수 Value를 공유하기 위한 LifeCycle 정의.
class JwtServerControllerTest {

    @Autowired
    private JwtServerService service;

    private static Map<String, String> map = new HashMap<>();

    @DisplayName("1. Token 생성 API - login()")
    @Test
    void 토큰생성() {
        JwtRequestServerDTO beforeDTO = JwtRequestServerDTO.builder()
                .userId("test")
                .userPw("1234")
                .build();

        JwtResponseServerDTO afterDTO = service.loginWithToken(beforeDTO);

        System.out.println("afterDTO Result = " + afterDTO);

        Assertions.assertEquals(beforeDTO.getUserId(), afterDTO.getUserId());

//        map.put("at", afterDTO.getAccessToken());

        // 2. 토큰 유효성 검사.
        String before_param = "beforeParam"; // param
        String header = "Bearer " + afterDTO.getAccessToken(); //header Request
        System.out.println("header = " + header);

        String after_param = service.getWithToken(header, before_param);
        System.out.println("after_param = " + after_param);

        Assertions.assertEquals(before_param, after_param);

    }// 1. 토큰 생성

//    @DisplayName("2. 토큰유효성 검사 + 파라미터 전송 - 인증된 유저만 API 사용(getWithToken())")
//    @Test
//    void api_테스트() {
//        String before_param = "beforeParam"; // param
//        String header = "Bearer " + map.get("at"); //header Request
//        System.out.println("header = " + header);
//
//        String result = service.getWithToken(header, before_param); // result param
//
//        System.out.println("result = " + result);
//
//        Assertions.assertEquals(before_param, result);
//    }

}//class