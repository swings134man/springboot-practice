package boot.bootprac.jwt_server.controller;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import boot.bootprac.jwt_server.domain.JwtResponseServerDTO;
import boot.bootprac.jwt_server.service.JwtServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/************
 * @info : Jwt Controller - Server Side
 * @name : JwtServerController
 * @date : 2023/03/20 6:27 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/jwt/server/")
public class JwtServerController {

    private final JwtServerService service;

    @PostMapping("v1/login")
    public JwtResponseServerDTO login(@RequestBody JwtRequestServerDTO inDTO) {
        if(ObjectUtils.isEmpty(inDTO)) {
            throw new IllegalArgumentException("요청 Parameter is NULL");
        }
        JwtResponseServerDTO result = service.loginWithToken(inDTO);
        return result;
    }

    // only AT - get v1
    @GetMapping("v1/getWith")
    public String getWithToken(@RequestHeader("Authorization") String header,
                               @RequestParam String param
                             ) {
        String[] headerSplit = header.split(" ");

        if(!headerSplit[0].equals("Bearer")) {
            throw new RequestRejectedException("토큰 요청형식이 잘못됨.");
        }else if(header.isEmpty()) {
            throw new RequestRejectedException("Authorization Header 요청이 잘못됨");
        }

        String result = service.getWithToken(header, param);
        return result;
    }// get


    // AT, RT - get v2
    // Test 용이기에 Cookie 대신 refresh Custom Header 로 RT 를 전달 받음.
    // Request = Header: Authorization: Bearer {Atoken}, refresh: Bearer {Rtoken}
    // 만약 AT가 만료라면, RT 검증 후 AT 재발급 및 Header로 AT 재전송.
    // RT 가 만료라면, 재 로그인.
    @GetMapping("v2/getWith")
    public ResponseEntity<String> getWithToken2(@RequestHeader("Authorization") String at,
                                                @RequestHeader("refresh") String rt,
                                                @RequestParam String param
                              ) {
        System.out.println("AT = " + at);
        System.out.println("RT = " + rt);

        String[] headSplit = at.split(" "); //0=Bearer, 1=token

        // Request Exception
        if(!headSplit[0].equals("Bearer")) {
            throw new RequestRejectedException("토큰 요청형식이 잘못됨.");
        }else if(at.isEmpty()) {
            throw new RequestRejectedException("Authorization Header 요청이 잘못됨");
        }

        // Service
        Map<String, String> withToken2 = service.getWithToken2(at, rt, param);

        return ResponseEntity.ok().header("Access_token", withToken2.get("newAT"))
                .body(withToken2.get("returnValue"));
    }// AT, RT Check

    // AOP 를 통한 Token 값 확인 후 요청 로직 수행.
    @GetMapping("v3/getWith")
    public Map<String, String> aopGetWith(@RequestHeader("Authorization") String at,
                                          @RequestHeader("refresh") String rt,
                                          @RequestParam String param) {

        log.info("AOP Controller 호출 됨.");
        log.info("AOP Controller Param = {}", param);

        Map<String, String> map = new HashMap<>();
        map.put("returnValue", param);

        return map;
    }

}//class
