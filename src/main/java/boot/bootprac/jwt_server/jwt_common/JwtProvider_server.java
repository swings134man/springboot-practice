package boot.bootprac.jwt_server.jwt_common;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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
     * Token 생성(Generate)
     * @param inDTO
     * @return Map<String, String>
     */
    public Map<String, String> generateToken(JwtRequestServerDTO inDTO) {
        Map<String, String> map = new HashMap<>();
        // Date
        Date date = new Date(System.currentTimeMillis() + (30 * 1000)); // 30 sec
        Date rtDate = new Date(System.currentTimeMillis() + (30 * 60 * 1000)); // 30 min

        if(inDTO.getUserId().equals("null") || inDTO.equals("")) {
            throw new NullPointerException("JwtRequestServerDTO userId=null");
        }

        // AT
        String token = JWT.create()
                .withSubject(inDTO.getUserId())
                .withClaim("userId", inDTO.getUserId())
                .withExpiresAt(date)// Expire=30초
                .sign(ALGORITHM_DECODE);

        // RT
        String refreshToken = JWT.create()
                .withSubject(inDTO.getUserId())
                .withClaim("userId", inDTO.getUserId())
                .withExpiresAt(rtDate)
                .sign(ALGORITHM_DECODE);

        // Tokens Refresh
        map.put("access_token", token);
        map.put("refresh_token", refreshToken);
        map.put("expired_time", String.valueOf(date));

        return map;
    }

    /**
     * Token 유효성 검증 Logic
     * @param at(Access Token)
     */
    public boolean validateToken(String at) {
        boolean status = false;
        try {
            JWT.require(ALGORITHM_DECODE)
                    .build()
                    .verify(at);
            return true;
        }catch (Exception e){
            log.warn("토큰이 유효하지 않습니다. {}", e.getMessage());
            return status;
        }

    }// Token 유효성 검증

    // RT Valid
    public String refreshTokenValidation(String refreshToken) {
        try{
            DecodedJWT verify = JWT.require(ALGORITHM_DECODE)
                    .build()
                    .verify(refreshToken);

            // return : New AT
            Map<String, Claim> claims = verify.getClaims();
            Claim userId = claims.get("userId");

            String token = JWT.create()
                    .withSubject(userId.asString())
                    .withClaim("userId", userId.asString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 1000)))// Expire=30초
                    .sign(ALGORITHM_DECODE);

            return token;
        }catch (Exception e) {
            log.warn("Refresh Token이 Expired 됨. 재 로그인 필요.");
            return null;
        }
    } // RT Valid and Returning New AT

    // Only Check RT
    public boolean checkOnlyRt(String refreshToken) {
        boolean status = false;

        try{
            DecodedJWT verify = JWT.require(ALGORITHM_DECODE)
                    .build()
                    .verify(refreshToken);
            return true;
        }catch (Exception e) {
            log.warn("Refresh 유효하지 않음. -> AT 재발급 필요.");
            return status;
        }
    }

}//class
