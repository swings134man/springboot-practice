package boot.bootprac.jwt_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/************
 * @info : Jwt Response DTO - Server Side
 * @name : JwtResponseDTO
 * @date : 2023/03/20 5:56 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseServerDTO {
    private String accessToken;
    private String refreshToken;

    private String token_type;
    private String expireTime;

    private String userId;
    private String statusCode;

    // msg
    private String msg;
}
