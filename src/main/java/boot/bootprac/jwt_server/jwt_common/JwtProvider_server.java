package boot.bootprac.jwt_server.jwt_common;

import boot.bootprac.jwt_server.domain.JwtRequestServerDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/************
 * @info : Jwt Server Side Token 공통 Logic
 * @name : JwtProvider_server
 * @date : 2023/03/20 5:58 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : Token 발급, 검증 공통 로직 클래스.
 *
 *  - Type : JWT
 *  - algorithm : HMAC256
 *  - Secret Key : JwtServerAPItestSecretKEY(Base64)
 *
 *  - JWT 3 part Ex : Header(Base64).payload(Base64).secret
 ************/
@Slf4j
@Component
public class JwtProvider_server {

    // SECRET_KEY - yml 파일이나, 노출되지 않는곳에 보관하여야 함.
    private static String SECRET_KEY =
            Base64.getEncoder().encodeToString("JwtServerAPItestSecretKEY".getBytes());

    // Decode Algorithm - 서명
    // - Secret Key(암호화키)를 알고리즘을 통해서 암호화.
    // - 알고리즘과 어떤 인코딩인지 알아도 -> 시크릿키의 내용을 모르면 Decode 할 수 없음.
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
     * -> Refactor(2023.03.24): Exception 처리 (JWT/JWTVerifier/verify())에 Exception 종류 포함되어있음.
     * @param at(Access Token)
     */
    public boolean validateToken(String at) {
        boolean status = false;
        try {
            JWT.require(ALGORITHM_DECODE)
                    .build()
                    .verify(at); // Algorithm.HMAC256(SECRET_KEY) 또한 가능.
            return true;
        }catch (AlgorithmMismatchException ale) {
            log.warn("Token Header에 명시된 알고리즘과 다릅니다.");
            return status;
        }catch (SignatureVerificationException sve) {
            log.warn("Token의 서명(Signature)이 유효하지 않습니다.");
            return status;
        }catch (TokenExpiredException tee) {
            log.warn("해당 Token은 Expired 되었습니다.");
            return status;
        }catch (InvalidClaimException ice) {
            log.warn("Claim의 기대값과는 다른값이 존재합니다. = {}", ice.getMessage());
            return status;
        }

//        catch (Exception e){
//            log.warn("토큰이 유효하지 않습니다. {}", e.getMessage());
//            return status;
//        }
    }// Token 유효성 검증


    /**
     * @info    : RT 검증 및 새로운 AT 발급
     * @name    : refreshTokenValidation
     * @date    : 2023/03/24 1:57 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : Refactoring : Exception Handling(2023.03.24)
     * - RT 검증과 검증결과에 따른 새로운 AT 발급 및 Return
     * - 새로운 AT 존재하지 않을시 : null Return
     */
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
        }catch (AlgorithmMismatchException ale) {
            log.warn("Token Header에 명시된 알고리즘과 다릅니다.");
            return null;
        }catch (SignatureVerificationException sve) {
            log.warn("Token의 서명(Signature)이 유효하지 않습니다.");
            return null;
        }catch (TokenExpiredException tee) {
            log.warn("해당 Refresh Token은 Expired 되었습니다.");
            return null;
        }catch (InvalidClaimException ice) {
            log.warn("Claim의 기대값과는 다른값이 존재합니다. = {}", ice.getMessage());
            return null;
        }

//        catch (Exception e) {
//            log.warn("Refresh Token이 Expired 됨. 재 로그인 필요.");
//            return null;
//        }
    }


    /**
     * @info    : Only Check RT
     * @name    : checkOnlyRt
     * @date    : 2023/03/24 1:56 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : refreshToken(String) - Only Token Value(!NOT INCLUDED Bearer!)
     * @return  : boolean
     * @Description : RT 의 체크만 수행함.
     */
    public boolean checkOnlyRt(String refreshToken) {
        boolean status = false;

        try{
            DecodedJWT verify = JWT.require(ALGORITHM_DECODE)
                    .build()
                    .verify(refreshToken);
            return true;
        }catch (AlgorithmMismatchException ale) {
            log.warn("Token Header에 명시된 알고리즘과 다릅니다.");
            return status;
        }catch (SignatureVerificationException sve) {
            log.warn("Token의 서명(Signature)이 유효하지 않습니다.");
            return status;
        }catch (TokenExpiredException tee) {
            log.warn("Refresh 유효하지 않음.(Expired) -> AT 재발급 필요.");
            return status;
        }catch (InvalidClaimException ice) {
            log.warn("Claim의 기대값과는 다른값이 존재합니다. = {}", ice.getMessage());
            return status;
        }
//        catch (Exception e) {
//            log.warn("Refresh 유효하지 않음. -> AT 재발급 필요.");
//            return status;
//        }
    }// checkOnlyRt

}//class
