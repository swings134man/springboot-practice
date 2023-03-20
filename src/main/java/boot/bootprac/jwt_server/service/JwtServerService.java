package boot.bootprac.jwt_server.service;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import boot.bootprac.jwt_server.domain.JwtResponseServerDTO;
import boot.bootprac.jwt_server.jwt_common.JwtProvider_server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    // Login - Token 생성
    public JwtResponseServerDTO loginWithToken(JwtRequestServerDTO inDTO) {
        // 예외 - > NULL Check는 Controller Layer에서 이루어짐.

        Map<String, String> tokenResult = jwtProviderServer.generateToken(inDTO);

        JwtResponseServerDTO outDTO = JwtResponseServerDTO.builder()
                .accessToken(tokenResult.get("access_token"))
                .refreshToken("")
                .userId(inDTO.getUserId())
                .statusCode("200")
                .msg("Token 생성 완료")
                .token_type("Bearer")
                .expireTime("30Sec")
                .build();

        return outDTO;
    }// loginWithToken

    // Token 검증 및 사용예시
    public String getWithToken(String header, String param) {
        // Header : Authorization - Bearer {Token}
        String[] headerSplit = header.split(" "); //0=Bearer, 1=Token Value

        jwtProviderServer.validateToken(headerSplit[1]);

        return param;
    }//getWithToken

}//class
