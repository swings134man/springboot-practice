package boot.bootprac.jwt_server.jwt_common;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/************
 * @info : Jwt Server Side 공통 Logic
 * @name : JwtProvider_server
 * @date : 2023/03/20 5:58 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Slf4j
@Component
public class JwtProvider_server {

    // SECRET_KEY
    private static String SECRET_KEY =
            Base64.getEncoder().encodeToString("JwtServerAPItestSecretKEY".getBytes());

    // Decode Algorithm
    private static final Algorithm ALGORITHM_DECODE = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Token 유효성 검증 Logic
     * @param token(String)
     */
    public boolean validateToken(String token) {
        boolean status = false;
        try {
            JWT.require(ALGORITHM_DECODE)
                    .build()
                    .verify(token);
            return true;
        }catch (Exception e){
            log.warn("토큰이 유효하지 않습니다. {}", e.getMessage());
            return status;
        }

    }// Token 유효성 검증


    /**
     * Token 생성(Generate)
     * @param inDTO
     * @return Map<String, String>
     */
    public Map<String, String> generateToken(JwtRequestServerDTO inDTO) {
        Map<String, String> map = new HashMap<>();
        // Date
        Date date = new Date(System.currentTimeMillis() + (30 * 1000));

        if(inDTO.getUserId().equals("null") || inDTO.equals("")) {
            throw new NullPointerException("JwtRequestServerDTO userId=null");
        }

        // Token 생성
        String token = JWT.create()
                .withSubject(inDTO.getUserId())
                .withClaim("userId", inDTO.getUserId())
                .withExpiresAt(date)// Expire=30초
                .sign(ALGORITHM_DECODE);



        map.put("access_token", token);
        map.put("expired_time", String.valueOf(date));
        // TODO : Refresh Token 추가 예정.

        return map;
    }

}//class
