package boot.bootprac.jwt_server.controller;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import boot.bootprac.jwt_server.domain.JwtResponseServerDTO;
import boot.bootprac.jwt_server.service.JwtServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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

}//class
