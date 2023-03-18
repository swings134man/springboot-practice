package boot.bootprac.jwt_request.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/************
 * @info : Jwt API 테스트를 위한 Response DTO
 * @name : JwtResponseDTO
 * @date : 2023/03/18 4:23 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {

    private String accessToken;
    private String refreshToken;

    private String token_type;
    private String expireTime;

    private String userId;
    private String statusCode;

    // msg
    private String msg;
}
