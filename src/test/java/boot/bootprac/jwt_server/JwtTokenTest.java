package boot.bootprac.jwt_server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

/************
 * @info : JWT Token Test
 * @name : JwtTokenTest
 * @date : 2023/03/21 2:27 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
class JwtTokenTest {


    private void printObj(String token) {
//        String[] tokens = token.split(" ");
    }

    @DisplayName("1. jjwt Token Test")
    @Test
    void jjwt_test() {
        // Key - TestSecretKey
        String key = "TestSecretKey12345678TestSecretKey123456789012"; // byte size가 256 이상 -> HS256을 쓰기 때문.

        // 1. Token 생성.
        Claims claims = Jwts.claims();
        claims.put("userId", "testId");
        claims.put("userEmail", "test@mail.com");

        String token = Jwts.builder().
                addClaims(claims)
                .setSubject((String) claims.get("userId"))
                .setExpiration(new Date(System.currentTimeMillis() + (30 * 1000)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();// Token 생성.

        System.out.println("token = " + token);

        // 2. Token 검증 및 decode
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key).parseClaimsJws(token);
        System.out.println("claimsJws = " + claimsJws);

        String afterUserId = (String) claimsJws.getBody().get("userId");

        Assertions.assertEquals("testId", afterUserId); //UserId
    } // jjwt


    @DisplayName("2. java-jwt")
    @Test
    void jwt_test() {

        String secretKey = "TestSecretKey";

        // 1. Token 생성.
        String token = JWT.create()
                .withClaim("userId", "testId")
                .withClaim("userEmail", "test@mail.com")
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 1000)))
                .withSubject("testId")
                .sign(Algorithm.HMAC256(secretKey));

        System.out.println("token = " + token);

        // 2. Token Decode
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);

        // Claims 로 따로 parsing 하지 않으면 byte code만 확인됨
        Map<String, Claim> claims = verify.getClaims();

        // Decode 출력
        System.out.println("verify = " + verify);
        System.out.println("claims = " + claims);

        Claim userId = verify.getClaim("userId");
        System.out.println("userId.asString() = " + userId.asString());

    }


}
