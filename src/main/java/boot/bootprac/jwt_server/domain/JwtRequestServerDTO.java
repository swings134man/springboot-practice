package boot.bootprac.jwt_server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/************
 * @info : Jwt Request DTO - Server Side
 * @name : JwtRequestDTO
 * @date : 2023/03/20 5:56 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequestServerDTO {
    private String userId;
    private String userPw;
}
