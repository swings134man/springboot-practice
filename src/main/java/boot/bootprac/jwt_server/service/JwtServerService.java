package boot.bootprac.jwt_server.service;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import boot.bootprac.jwt_server.domain.JwtResponseServerDTO;
import boot.bootprac.jwt_server.jwt_common.JwtProvider_server;
import boot.bootprac.jwt_server.repository.JwtServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/************
 * @info : Jwt Server Side Logic
 * @name : JwtServerService
 * @date : 2023/03/20 6:04 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServerService {

    private final JwtProvider_server jwtProviderServer;
    private final JwtServerRepository repository;


    /**
     * @info    : Login - Token 생성
     * @name    : loginWithToken
     * @date    : 2023/03/24 1:45 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : JwtRequestServerDTO
     * @Description : AT,RT Token 생성 로직 (로그인)
     */
    public JwtResponseServerDTO loginWithToken(JwtRequestServerDTO inDTO) {
        // 예외 - > NULL Check는 Controller Layer에서 이루어짐.

        Map<String, String> tokenResult = jwtProviderServer.generateToken(inDTO);

        JwtResponseServerDTO outDTO = JwtResponseServerDTO.builder()
                .accessToken(tokenResult.get("access_token"))
                .refreshToken(tokenResult.get("refresh_token"))
                .userId(inDTO.getUserId())
                .statusCode("200")
                .msg("Token 생성 완료")
                .token_type("Bearer")
                .expireTime(tokenResult.get("expired_time"))
                .build();

        return outDTO;
    }// loginWithToken


    /**
     * @info    : Token 검증 및 사용예시
     * @name    : getWithToken
     * @date    : 2023/03/24 1:44 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : header(Token Value)
     * @param   : param(String)
     * @Description : 해당 GET 요청시 AT 검증 후 성공시에만 Return
     */
    public String getWithToken(String header, String param) {
        // Header : Authorization - Bearer {Token}
        String[] headerSplit = header.split(" "); //0=Bearer, 1=Token Value

        boolean tokenVali = jwtProviderServer.validateToken(headerSplit[1]);
        if(!tokenVali) {
            throw new RuntimeException("해당 Token Expired 됨.");
        }

        return param;
    }//getWithToken

    /**
     * Token - AT, RT를 통한 검증 및 GET 로직 수행.
     * @param at(Access_token)
     * @param rt(Refresh_token)
     * @param param(String)
     * @return
     */
    public Map<String, String> getWithToken2(String at, String rt, String param)  {
        String newAT = "";
        // 1. AT 검증.
        String[] atSplit = at.split(" ");
        boolean atStatus = jwtProviderServer.validateToken(atSplit[1]);

        if(!atStatus) {
            // AT 만료시 RT 체크 후 재발급 OR RT 재발급.
            // newAT == null 일시 RT 재발급 필요 -> 재 로그인 필요.
            String[] rtSplit = rt.split(" ");
            newAT = jwtProviderServer.refreshTokenValidation(rtSplit[1]);

            if(newAT.equals("null")){
                throw new IllegalArgumentException("재 로그인 필요함.");
            }
        }

        // 2. Logic Flow - param 및 RT 전송해야함.
        Map<String, String> map = new HashMap<>();
        map.put("returnValue", param);
        map.put("newAT", newAT);

        return map;
    }

}//class
