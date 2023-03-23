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
 * @Description : Server Side JWT 토큰 발급 및 검증
 *
 * - 클라이언트가 사용하는것이 아닌, REST API 제공자의 Side (이후 발급 된 토큰으로 REST API 제공.)
 ************/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/jwt/server/")
public class JwtServerController {

    private final JwtServerService service;

    /**
     * @info    : Login - Token 생성
     * @name    : login
     * @date    : 2023/03/24 1:51 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : JwtRequestServerDTO: Login에 필요한{userId, userPw}
     * @return  : ResponseDTO
     * @Description : AT, RT 발급 및 로그인 후 필요 정보 Return
     */
    @PostMapping("v1/login")
    public JwtResponseServerDTO login(@RequestBody JwtRequestServerDTO inDTO) {
        if(ObjectUtils.isEmpty(inDTO)) {
            throw new IllegalArgumentException("요청 Parameter is NULL");
        }
        JwtResponseServerDTO result = service.loginWithToken(inDTO);
        return result;
    }

    /**
     * @info    : GET 요청 - Only AT
     * @name    : getWithToken
     * @date    : 2023/03/24 1:50 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : v1
     * @param   : header(Header(Authorization): Bearer AT)
     * @return  : String
     * @Description : AT 검증 후 성공시 Return, 실패시 Exception
     */
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


    /**
     * @info    : GET 요청 - AT, RT 포함.
     * @name    : getWithToken2
     * @date    : 2023/03/24 1:48 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : v2
     * @param   : at(Header: Bearer AT)
     * @param   : rt(Header: Bearer RT)
     * @param   : param(String) - logic
     * @return  :
     * @Description : Test 용이기에 Cookie 대신 refresh Custom Header 로 RT 를 전달 받음.
     *  - Request = Header: Authorization: Bearer {Atoken}, refresh: Bearer {Rtoken}
     *  - 만약 AT가 만료라면, RT 검증 후 AT 재발급 및 Header로 AT 재전송, RT 가 만료라면, 재 로그인.
     */
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


    /**
     * @info    : AOP 를 통한 Token 값 확인 후 요청 로직 수행.
     * @name    : aopGetWith
     * @date    : 2023/03/24 1:46 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : v3
     * @param   : at(Header: Bearer AT)
     * @param   : rt(Header: Bearer RT)
     * @param   : param(String) - logic
     * @return  : Map<String, String> : returnValue(Value), access_token(새로 발급한 Access_Token)
     * @Description :
     */
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
